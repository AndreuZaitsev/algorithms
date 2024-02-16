package rxjava

import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import log
import traceBlocking

/*
{ log("OnSuccess = $it") },
{ log("OnError = $it") },
{ log("OnComplete") },
{ log("OnSubscribe") },
*/

fun main() {
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

