package rxjava

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import log
import traceBlocking

fun main() {
    traceBlocking {
        liveChannels()
            //.subscribeOn(Schedulers.newThread()) // change above ^
            .doOnSubscribe { log("doOnSubscribe - Stop tracer[1]") } // main by default, otherwise by subscribeOn from client
            .subscribeOn(Schedulers.newThread())
            .doFinally { log("doFinally - Stop tracer[2]") } // io
            .doOnNext { _ -> log("event[5]") } // io

            .observeOn(Schedulers.single())
            .doOnNext { _ -> log("event[6]") } // single
            .doFinally { log("doFinally[2]"); it.countDown() } // single

            .subscribe(
                { log("OnSuccess = $it") }, // single
                { log("OnError = $it") },
                { log("OnComplete") }, // single
            )
    }
}

private fun liveChannels(): Observable<String> {
    log("out of rx")
    return Observable.defer {
        val channelsRequest = Single.defer {
            log("defer")
            Single.fromCallable { log("response");"response" }  // main by default, otherwise by subscribeOn from client
                .doOnEvent { t, t1 -> log("event[1]") } // main by default, otherwise by subscribeOn from client
                .observeOn(Schedulers.io())
        }
        Completable.fromCallable { log("response[2]");"" }  // main by default, otherwise by subscribeOn from client
            .doOnEvent { log("event[2]") } // main by default, otherwise by subscribeOn from client
            .timeout(1, TimeUnit.SECONDS)
            .andThen(channelsRequest)
            .doOnEvent { t, t1 -> log("event[3]") } // io
            .toObservable()
    }
        .doOnNext { t -> log("event[4]") }  // io
        .doOnSubscribe { log("pluto") } // main by default, otherwise by subscribeOn from client
}