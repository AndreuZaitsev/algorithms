package `2_Cancellation`

import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import log

fun main() = runBlocking {
    val job = launch {
        try {
            repeat(5) { i ->
                log("Coroutine: I'm working on step $i")
                delay(1000)
            }
        } finally {
            log("Coroutine: I'm being cancelled")
        }
    }

    delay(2500)
    log("Main: I'm tired of waiting")
    job.cancelAndJoin()
    log("Main: Coroutine is cancelled")
}
