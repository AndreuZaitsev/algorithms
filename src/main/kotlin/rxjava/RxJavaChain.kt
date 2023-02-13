package rxjava

import io.reactivex.Maybe
import io.reactivex.Single

fun main() {
//    FOO()
    println("------------")
    FOO2()
    println("------------")
//    FOO3()
    println("------------")
//    FOO4()
    println("------------")
    while (true) {

    }
}

private fun FOO() {
    Single.just(true)
        .doOnSubscribe { println("start") }
        .doOnTerminate { println("terminate") }


        .filter { isEnabled -> isEnabled }
        .flatMap {
            throw NullPointerException()
            Maybe.just("hey-hey")
                .doOnError { println("Error catch child") }
                .onErrorComplete()

        }
        .subscribe(
            { println(it) },
            { println("Error catch parent") }
        )
}

private fun FOO2() {
    Single.just(true)
        .filter { isEnabled -> isEnabled }
        .flatMap {
            throw NullPointerException()
            Maybe.just("hey-hey")
                .doOnError { println("Error catch child") }
                .onErrorComplete()

        }
        .doOnSubscribe { println("start") }
        .doOnTerminate { println("terminate") }
        .doOnError { println("Error catch parent") }

        .subscribe()
}

private fun FOO3() {
    Single.just(true)
        .filter { isEnabled ->
            throw NullPointerException()
            isEnabled }
        .flatMap {
            Maybe.just("hey-hey")
                .doOnError { println("Error catch child") }
                .onErrorComplete()

        }
        .doOnSubscribe { println("start") }
        .doOnTerminate { println("terminate") }
        .subscribe(
            { println(it) },
            { println("Error catch parent") }
        )
}

private fun FOO4() {
    Single.just(true)
        .doOnSubscribe { println("start") }
        .doOnTerminate { println("terminate") }
        .filter { isEnabled ->
            throw NullPointerException()
            isEnabled }
        .flatMap {
            Maybe.just("hey-hey")
                .doOnError { println("Error catch child") }
                .onErrorComplete()

        }

        .subscribe(
            { println(it) },
            { println("Error catch parent") }
        )
}