package rxjava_internals

import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import rxjava_internals.GuideDetailsErrorState.GuideFetchedButNoChannels
import rxjava_internals.GuideDetailsErrorState.NoErrors
import java.util.concurrent.TimeUnit
import kotlin.math.pow

sealed interface GuideDetailsErrorState {

    object GuideFetchedButNoChannels : GuideDetailsErrorState

    object NoErrors : GuideDetailsErrorState

    object GuideNotFetched : GuideDetailsErrorState

    companion object {

        fun from(throwable: Throwable): GuideDetailsErrorState =
            if (throwable is NoChannelsException) GuideFetchedButNoChannels else GuideNotFetched
    }
}

private val _guideDetailsErrorState = BehaviorSubject.createDefault<GuideDetailsErrorState>(NoErrors)


fun main() {
    printLog("Starting at thread")
    launchLoading()

    _guideDetailsErrorState.hide()
        .subscribe(
            { state ->
                printLog("error state: $state")
                if (state is GuideFetchedButNoChannels) launchLoading()
            },
            { printLog("Error observing errors $it") }
        )
    while (true) {

    }
}

private fun launchLoading() {
    loadData()
        .subscribe(
            { printLog("received: $it") },
            { printLog("Error catch parent $it") }
        )
}

private data class Response(val list: List<String> = emptyList())

private var counter = 2
private var isSecond = false

private val responseProvider: Single<Response>
    get() = when (counter--) {
        2 -> Single.error(IllegalArgumentException("500"))
        1 -> Single.error(IllegalArgumentException("400"))
        else -> {
            counter = 2
            val response = if (isSecond) Response(listOf("A")) else Response()
            isSecond = true
            Single.just(response)
        }
    }


private fun loadData(): Single<Response> =
    Single.defer { responseProvider }
        .retryLoading()
        .doOnSuccess { response ->
            printLog("doOnSuccess: $response")
            _guideDetailsErrorState.onNext(NoErrors)
        }
        .doOnError { error ->
            printLog("doOnError: $error")
            _guideDetailsErrorState.onNext(GuideDetailsErrorState.from(error))
        }

private fun Single<Response>.retryLoading(
    scheduler: Scheduler = Schedulers.io(),
    timerScheduler: Scheduler = Schedulers.computation(),
    attempts: Int = 2
): Single<Response> =
    retryWhen { errors ->
        errors
            .zipWith(Flowable.range(1, attempts + 1)) { error, retryCount ->
                when {
                    retryCount > attempts -> {
                        printLog("attempts finished $retryCount")
                        throw error
                    }

                    else -> {
                        printLog("next attempt $retryCount")
                        retryCount
                    }
                }
            }
            .flatMap { retryCount ->
                Flowable.timer(2.toDouble().pow(retryCount.toDouble()).toLong(), TimeUnit.SECONDS, timerScheduler)
            }

    }
        .doOnSuccess { response ->
            if (response.list.isEmpty()) throw NoChannelsException()
        }
        .subscribeOn(scheduler)

private class NoChannelsException : Throwable("Guide is fetched with empty channels")

private fun printLog(str: String) {
    println("${Thread.currentThread().name}:::$str")
}