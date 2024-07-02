package `2_Cancellation`

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable
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

    scope.launch(Job()) {
        delay(2_000)
        log("scope[2]")
    }

    scope.launch(NonCancellable) {
        delay(2_000)
        log("scope[3]")
    }

    delay(1_500)
    log("canceling the scope")
    scope.coroutineContext.cancelChildren()

    delay(1_000)
    scope.launch {
        log("scope[4]")
    }

    log("end")
}