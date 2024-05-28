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
    val sut = RxJavaMain2()
    val thread = Thread { sut.init() }
    val stopThread = Thread {
        Thread.sleep(2000)
        println(">>>>>>>>>>>>>>>>>>>>>>>>>>> TERMINATE CONNECTION <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<")
        sut.dispose()
    }

    thread.start()
    stopThread.start()
    while (process.get()) {
        // Keep main thread alive
    }
}

private class RxJavaMain2 {

    private val isFeatureEnabled
        get() = Single.fromCallable {
            Thread.sleep(500)
            true
//            throw IllegalArgumentException()
        }

    private val disconnectedSignal = connection.filter { isConnected -> isConnected.not() }

    private val eventsSource by lazy {
        Observable.fromArray(*Event.values())
            .subscribeOn(Schedulers.io())
            .takeUntil(disconnectedSignal)
    }

    private var disposable: Disposable? = null

    fun init() {
        isFeatureEnabled
            .toObservable()
            .flatMap { isEnabled ->
                if (isEnabled) {
                    val s1 = eventsSource.launchFirstFeature()
                    val s2 = getMetadataObservable()
                        .doOnNext {
                            Thread.sleep(100)
                            println(it)
                        }
                    Observable.merge(s1, s2)
                } else {
                    eventsSource.launchSecondFeature()
                }
            }
            .doOnNext {

            }
            .doFinally { process.set(false) }
            .applyLogging("Main-Stream")
            .subscribe(
                { /* Nothing to do */ },
                { error -> println("Main stream handle error $error") }
            )
    }

    fun dispose() {
        connection.onNext(false)
        disposable?.dispose()
        disposable = null
    }

    private fun Observable<Event>.launchFirstFeature(): Observable<Double> = this
        .applyLogging("First Stream")
        .switchMap { event ->
            println("event(1) = $event")
            getNumbersObservable(event)
        }
        .doOnNext {
            Thread.sleep(100)
            println("number(1) = $it")
        }
        .map {
            it.toDouble()
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
        .doOnError { "Second stream handle error $it" }


    private fun getNumbersObservable(event: Event): Observable<Int> {
        val numbers = event.values.toList()
        return Observable.fromArray(*numbers.toTypedArray())
    }

    private fun getMetadataObservable(): Observable<String> {
        val list = (1..100).toList().map { "metadata $it" }
        return Observable.fromArray(*list.toTypedArray())
    }
}
