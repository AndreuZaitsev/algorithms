package `1_Scope1_2`

import kotlinx.coroutines.*
import log


// Puzzler 1.2: Coroutine Context Switching
// Question: What is the output of this code snippet, and why?
fun main(): Unit = runBlocking {
    val context1 = newSingleThreadContext("Context1")
    val context2 = newSingleThreadContext("Context2")

    launch(context1) {
        log("Starting in context1")
        withContext(context2) {
            log("Switched to context2")
        }
        log("Back to context1")
    }
}
