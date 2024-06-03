package `2_Cancellation`

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import log

private val scope = CoroutineScope(SupervisorJob())

fun main(): Unit = runBlocking {
    scope.launch {
        delay(1000)
        log("scope[1]")
    }
    scope.launch {
        delay(2000)
        log("scope[2]")
    }


    delay(1500)
    log("canceling the scope")
    scope.cancel()

    delay(1000)

    scope.launch {
        log("scope[3]")
    }

    log("end")
}
