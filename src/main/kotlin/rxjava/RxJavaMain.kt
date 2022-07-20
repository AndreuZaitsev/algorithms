package rxjava

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

val connection = BehaviorSubject.createDefault<Boolean>(true)

fun main() {
    val sut = RxJavaMain()

    Thread {
        sut.init()
    }.start()

    Thread {
        Thread.sleep(1000)
        println(">>>>>>>>>>>>>>>>>>>>>>>>>>> TERMINATE CONNECTION <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<")
        sut.dispose()
    }.start()

//    Thread {
//        Thread.sleep(1500)
//        connection.onNext(true)
//        println("resume")
//        sut.init()
//    }.start()

    while (true) {

    }
}

class RxJavaMain {

    private val singleBool
        get() = Single.fromCallable {
            Thread.sleep(500)
            true
        }

    private val disconnectedSignal = connection
        .hide()
        .filter { isConnected -> isConnected.not() }

    private val eventsSource by lazy {
        Observable.fromArray("first", "second", "third")
            .subscribeOn(Schedulers.io())
            .takeUntil(disconnectedSignal)
    }

    var disposable: Disposable? = null

    fun init() {
        disposable =
            singleBool
                .flatMapObservable { isBool ->
                    eventsSource.apply { if (isBool) switchTo1() else switchTo2() }
                }
                .doOnSubscribe { println("doOnSubscribe common") }
                .doFinally { println("doFinally common") }
                .subscribe(
                    { /* Nothing to do */ },
                    { error -> println("Error while preparing ads for tracking $error") }
                )
    }

    fun dispose() {
    }

    private fun getNumbersObservable(event: String): Observable<Int> {
        val numbers = when (event) {
            "first" -> 1..10
            "second" -> 200..210
            "third" -> 300..310
            else -> throw IllegalStateException()
        }.toList()
        return Observable.fromArray(*numbers.toTypedArray())
    }

    private fun Observable<String>.switchTo1() {
        this
            .doOnSubscribe { println("doOnSubscribe 1") }
            .doFinally { println("doFinally 1") }
            .switchMap { event ->
                println("event(1) = $event")
                getNumbersObservable(event)
            }
            .doOnNext {
                Thread.sleep(100)
                println("number(1) = $it")
            }
            .doOnError { error -> println("Error while tracking (1) ads $error") }
            .subscribe({}, {})
    }

    private fun Observable<String>.switchTo2() {
        this
            .doOnSubscribe { println("doOnSubscribe 2") }
            .doFinally { println("doFinally 2") }
            .switchMap { event ->
                println("event(2) = $event")
                getNumbersObservable(event)
            }
            .doOnNext {
                Thread.sleep(100)
                println("number(2) = $it")
            }
            .doOnError { error -> println("Error while tracking (2) ads $error") }
            .subscribe()
    }
}
