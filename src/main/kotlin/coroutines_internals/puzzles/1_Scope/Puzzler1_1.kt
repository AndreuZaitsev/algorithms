package `1_Scope1_1`

import kotlinx.coroutines.*
import log

fun main(): Unit = runBlocking {
    val job = launch {
        delay(500)
        log("Coroutine 1")
    }

    coroutineScope {
        launch {
            delay(1000)
            log("Coroutine 2")
        }
    }

    log("End of main")
}