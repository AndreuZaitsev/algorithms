package rxjava

import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

fun main() {
    println("Starting at thread" + "-----" + Thread.currentThread().name)
    FOO()
        .observeOn(Schedulers.computation())
        .subscribe(
            { printLog(it) },
            { printLog("Error catch parent") }
        )
    while (true) {

    }
}

private fun FOO(): Maybe<String> {
    return Single.just(true)
        .doOnEvent { b, throwable ->
            printLog("item")
        }
        .subscribeOn(Schedulers.io())
        .doOnSubscribe { printLog("start") }
        .doOnTerminate { printLog("terminate") }
        .filter { isEnabled -> isEnabled }
        .flatMap {
//            throw NullPointerException()
            Maybe.just("hey-hey")
                .doOnError { printLog("Error catch child") }
                .onErrorComplete()

        }
}

private fun printLog(str: String) {
    println(str + "-----" + Thread.currentThread().name)
}