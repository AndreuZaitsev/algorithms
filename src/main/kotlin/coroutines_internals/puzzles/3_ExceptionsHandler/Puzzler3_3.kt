package `3_ExceptionsHandler3_1`

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import log

fun main() = runBlocking {
    val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        log("Caught $exception")
    }

    val scope = CoroutineScope(SupervisorJob())
    scope.launch(exceptionHandler) {
        launch {
            log("Coroutine 1 starts")
            delay(500)
            log("Coroutine 1 throws an exception")
            throw RuntimeException("Coroutine 1 exception")
        }

        scope.launch {
            log("Coroutine 2 starts")
            delay(5000)
            log("🍬Coroutine 2 completes successfully")
        }
    }

    scope.launch(exceptionHandler) {
        launch {
            log("Coroutine 3 starts")
            delay(500)
            log("Coroutine 3 throws an exception")
            throw RuntimeException("Coroutine 3 exception")
        }

        launch {
            log("Coroutine 4 starts")
            delay(1000)
            log("🍬Coroutine 4 completes successfully")
        }
    }

    val allJobs = scope.coroutineContext[Job]?.children.orEmpty().toList().toTypedArray()
    joinAll(*allJobs)
}