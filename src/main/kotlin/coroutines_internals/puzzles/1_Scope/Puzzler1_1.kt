package `1_Scope1_1`

import kotlinx.coroutines.*
import log

// Puzzler 1.1: Coroutine Scope Confusion
// Question: What is the order of the outputs, and why?
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