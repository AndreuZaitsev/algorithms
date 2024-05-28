package rxjava_internals

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.internal.disposables.CancellableDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import log
import traceBlocking

/*
{ log("OnSuccess = $it") },
{ log("OnError = $it") },
{ log("OnComplete") },
{ log("OnSubscribe") },
*/
private val compositeDisposable = CompositeDisposable()

private fun getFeature() = Single
    .fromCallable {
        log("featuring...")
        true
    }
    .delay(5000, TimeUnit.MILLISECONDS)
    .subscribeOn(Schedulers.io())

fun main() {
    traceBlocking {
        Observable
            .fromCallable {
                log("do smth...")
                1
            }
            .doOnNext { log("before switch") }
            .switchMapCompletable {
                log("switching..")
                if (it == 1) {
                    getFeature()
                        .observeOn(Schedulers.newThread())
                        .doOnSuccess { log("resume") }
                        .ignoreElement()
                } else {
                    Completable.complete()
                }
            }
            .subscribe {
                log("completed")
            }
    }

    traceBlocking {
        Observable.fromCallable { log("0"); "value" } // io
            .doOnNext { log(it) }
            .flatMap {
                Observable
                    .fromCallable { log("0-0"); "flat value" }
                    .delay(3000, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.computation())
            }
            .doOnNext { log("map") } // computation
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.single()) // change
            .doOnDispose {
                Schedulers.io().scheduleDirect {
                    log("disposed")
                }
            } // calling thread of  compositeDisposable.dispose()
            .doFinally {
                Schedulers.io().scheduleDirect {
                    log("do finally")
                }
            } // main thread if via compositeDisposable.dispose()
            .subscribe { log("handle") } // computation
            .apply {
                compositeDisposable.add(this)
            }


        Thread.sleep(1000)
        log("dispose")
        compositeDisposable.dispose()
    }

    traceBlocking {
        Observable.fromCallable {
            log("0")
            "value"
        } // io
            .doOnNext { log(it) } // io
            .observeOn(Schedulers.computation()) // change
            .doOnNext { log(it) } // computation
            .doOnSubscribe { log("1") } // io
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.newThread()) // change
            .doOnSubscribe { log("2") } // calling thread
            .doFinally { log("finally"); it.countDown() } // single
            .subscribe { log(it + "finish") } // new thread
    }


    traceBlocking {
        var disposable: Disposable? = null
        CancellableDisposable {
            log("Dispose from CancellableDisposable")
            Schedulers.io().scheduleDirect { disposable?.dispose() }
        }.apply { compositeDisposable.add(this) }

        disposable = Observable.just(3)
            .delay(5000, TimeUnit.MILLISECONDS)
            .observeOn(Schedulers.io())
            .doFinally {
                log("do finally") // What thread is here?
                it.countDown()
            }
            .subscribeOn(Schedulers.io())
            .subscribe { log("on next") }

        Thread.sleep(1000)
        compositeDisposable.dispose()
    }

    traceBlocking {
        Observable.just(3)
            .doOnNext { t1 -> log("EVENT 1") } // io

            .observeOn(Schedulers.single()) // downStream except doOnSubscribe
            .doOnNext { t1 -> log("EVENT 2") } // single
            .doOnSubscribe { log("doOnSubscribe") } // io
            .subscribeOn(Schedulers.io())
            .doOnNext { t1 -> log("EVENT 3") } // single
            .doOnSubscribe { log("doOnSubscribe2") } // computation
            .subscribeOn(Schedulers.computation())

            .doFinally { log("finally"); it.countDown() } // single

            .observeOn(Schedulers.newThread())
            .doOnNext { t1 -> log("EVENT 4") } // newThread
            .subscribe(
                { log("OnSuccess = $it") }, // newThread
                { log("OnError = $it") },
                { log("OnComplete") }, // newThread
                { log("OnSubscribe") }, // main
            )
    }

    traceBlocking {
        Single.just(3)
            .doOnSubscribe { log("doOnSubscribe") } // io
            .doOnEvent { t1, t2 -> log("event $t1") } // io
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { log("doOnSubscribe2") } // computation
            .doOnEvent { t1, t2 -> log("event $t1") } // io
            .subscribeOn(Schedulers.computation())
            .doFinally { log("finally"); it.countDown() } // io
            .subscribe { result -> log("onSuccess $result") } // io
    }

    traceBlocking {
        Single.just(3)
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { log("doOnSubscribe") } // computation
            .subscribeOn(Schedulers.computation())
            .doFinally { log("finally"); it.countDown() } // io
            .subscribe { result -> log("onSuccess $result") } // io
    }

    traceBlocking {
        case1()
            .observeOn(Schedulers.computation())
            .doFinally {
                log("DoFinally") // computation
                it.countDown()
            }
            .subscribe(
                /* onSuccess = */ { log("OnSuccess = $it") },  // computation
                /* onError = */ { log("OnError = $it") }, // computation
                /* onComplete = */ { log("OnComplete") }, // computation
            )
    }

    traceBlocking {
        Observable.fromCallable { 1 } // io
            .subscribeOn(Schedulers.io()) // affects up stream only
            .doOnSubscribe { log("doOnSubscribe") } // main
            .doFinally { it.countDown() } // io
            .subscribe(
                /* onNext = */ { log("OnSuccess = $it") },
                /* onError = */ { log("OnError = $it") },
                /* onComplete = */ { log("OnComplete") },
                /* onSubscribe = */ { log("OnSubscribe") }, // calling thread always, operators are useless here
            )
    }

    traceBlocking {
        Observable.fromCallable { 1 } // io
            .doOnSubscribe { log("doOnSubscribe") } // io
            .doFinally { it.countDown() } // io
            .subscribeOn(Schedulers.io())
            .subscribe(
                /* onNext = */ { log("OnSuccess = $it") },
                /* onError = */ { log("OnError = $it") },
                /* onComplete = */ { log("OnComplete") },
                /* onSubscribe = */ { log("OnSubscribe") }, // calling thread always, operators are useless here
            )
    }
}

private fun case1(): Maybe<String> {
    fun produceTrue(): Boolean {
        log("produce true")
        return true
    }

    fun produceResult(): String {
        log("produce result")
        return "hey-hey"
    }
    return Single
        //.just(produceTrue())  // main
        .fromCallable { produceTrue() } // io
        .subscribeOn(Schedulers.io())
        .doOnSubscribe { log("doOnSubscribe") } // main
        .doOnTerminate { log("doOnTerminate") } // io
        .filter { isEnabled ->
            log("filter") // io
            isEnabled
        }
        .flatMap {
            log("flatMap") // io
            Maybe.fromCallable { produceResult() } // io
                .doOnError { log("doOnError $it") } // io
                .onErrorComplete()
        }
}

private fun case2(): Maybe<Boolean> {
    fun produceCrash(): Boolean {
        log("produce true") // main
        throw Exception()
        return true
    }
    return Single
        .fromCallable { produceCrash() } // safe
        //.just(produceCrash())          // crashing anyway
        .toMaybe()
}

