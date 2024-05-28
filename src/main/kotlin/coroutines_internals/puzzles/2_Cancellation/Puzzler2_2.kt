package `2_Cancellation`

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import log

fun main() = runBlocking {
    val supervisor = SupervisorJob()

    with(CoroutineScope(coroutineContext + supervisor)) {
        val child = launch {
            try {
                log("Child is running")
                delay(Long.MAX_VALUE)
            } finally {
                log("Child is cancelled")
            }
        }

        delay(1000)
        log("Cancelling the supervisor")
        supervisor.cancel()
    }

    log("Parent is not cancelled")
}
