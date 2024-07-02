package `2_Cancellation`

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import log

private val scope = CoroutineScope(SupervisorJob())
fun main(): Unit = runBlocking {
    scope.launch {
        delay(1_000)
        log("scope[1]")
    }
    val job = scope.launch {
        while (true) {
            log("scope[2] is still running...")
        }
    }

    delay(1_500)
    log("canceling the scope")
    scope.coroutineContext.cancelChildren()

    delay(1_000)
    scope.launch {
        log("scope[4]")
    }
    job.join()
    log("end")
}
