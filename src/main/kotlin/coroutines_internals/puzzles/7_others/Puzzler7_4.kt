package `5_AsyncAwait`

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.runBlocking
import log

var counter = 0

/**
 * hint
 * use synchronized or change custom dispatcher
 * or mutex
 */
@OptIn(DelicateCoroutinesApi::class)
fun main() = runBlocking {
    val jobs = mutableListOf<Job>()
    val startTime = System.currentTimeMillis()
    val customDispatcher = newFixedThreadPoolContext(2, "CustomDispatcher")

    repeat(1_000) {
        jobs += launch(customDispatcher) {
            repeat(1_000) {
                counter++
            }
        }
    }
    joinAll(*jobs.toTypedArray())
    log("Counter: $counter , passed time = ${System.currentTimeMillis() - startTime}ms")
}