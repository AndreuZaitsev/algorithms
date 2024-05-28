package rxjava_internals

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.atomic.AtomicBoolean

private val connection = BehaviorSubject.createDefault(true)
private val process = AtomicBoolean(true)

fun main() {
    val sut = RxJavaMain()
    val thread = Thread { sut.init() }
    val stopThread = Thread {
        Thread.sleep(2000)
        println(">>>>>>>>>>>>>>>>>>>>>>>>>>> TERMINATE CONNECTION <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<")
        sut.dispose()
    }

    thread.start()
    while (process.get()) {
        // Keep main thread alive
    }
}

private class RxJavaMain {

    private val isFeatureEnabled
        get() = Single.fromCallable {
            Thread.sleep(500)
            true
        }

    private val disconnectedSignal = connection.filter { isConnected -> isConnected.not() }

    private val eventsSource by lazy {
        Observable.fromArray(*Event.values())
            .subscribeOn(Schedulers.io())
            .takeUntil(disconnectedSignal)
    }

    private var disposable: Disposable? = null

    fun init() {
        disposable = isFeatureEnabled.flatMapObservable { isFeatureEnabled ->
            eventsSource.apply {
                if (isFeatureEnabled)
                    launchFirstFeature()
                        .doFinally { process.set(false) }
                        .subscribe(
                            { /* Nothing to do */ },
                            { error -> println("Error in (1) $error") }
                        )
                else
                    launchSecondFeature()
                        .doFinally { process.set(false) }
                        .subscribe(
                            { /* Nothing to do */ },
                            { error -> println("Error in (2) $error") }
                        )
            }
        }
            .applyLogging("Main Stream")
            .subscribe(
                { /* Nothing to do */ },
                { error -> println("Error while preparing ads for tracking $error") }
            )
    }

    fun dispose() {
        connection.onNext(false)
        disposable?.dispose()
        disposable = null
    }

    private fun Observable<Event>.launchFirstFeature(): Observable<Int> = this
        .applyLogging("First Stream")
        .switchMap { event ->
            println("event(1) = $event")
            getNumbersObservable(event)
        }
        .doOnNext {
            Thread.sleep(100)
            println("number(1) = $it")
        }

    private fun Observable<Event>.launchSecondFeature(): Observable<Int> = this
        .applyLogging("Second Stream")
        .switchMap { event ->
            println("event(2) = $event")
            getNumbersObservable(event)
        }
        .doOnNext {
            Thread.sleep(100)
            println("number(2) = $it")
        }

    private fun getNumbersObservable(event: Event): Observable<Int> {
        val numbers = event.values.toList()
        return Observable.fromArray(*numbers.toTypedArray())
    }
}

fun <T : Any> Observable<T>.applyLogging(tag: String): Observable<T> =
    doOnSubscribe { println("$tag - SUBSCRIBE") }
        .doOnComplete { println("$tag - COMPLETE") }

enum class Event(val values: IntRange) {
    FIRST(1..10),
    SECOND(200..210),
    THIRD(300..310)
}