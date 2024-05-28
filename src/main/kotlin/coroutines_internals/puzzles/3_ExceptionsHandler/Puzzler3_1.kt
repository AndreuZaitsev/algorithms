package `3_ExceptionsHandler3_1`

import kotlinx.coroutines.*
import log

// Puzzler 3.1: Coroutine Exception Handling
// Question: What is the output of this code snippet, and why?

fun main() = runBlocking {
    val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        log("Caught $exception")
    }

    val job = GlobalScope.launch(exceptionHandler) {
        launch {
            log("Coroutine 1 starts")
            delay(500)
            log("Coroutine 1 throws an exception")
            throw RuntimeException("Coroutine 1 exception")
        }

        launch {
            log("Coroutine 2 starts")
            delay(1000)
            log("🍬Coroutine 2 completes successfully")
        }
    }
    job.join()
}