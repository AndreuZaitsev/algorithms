package `1_Scope1_2`

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import log


@OptIn(ExperimentalCoroutinesApi::class, DelicateCoroutinesApi::class)
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
