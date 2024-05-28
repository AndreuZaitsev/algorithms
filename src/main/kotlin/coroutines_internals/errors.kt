package coroutines_internals

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import log
import traceBlocking

lateinit var job: Job

fun main() {
    traceBlocking { latch ->
        CoroutineScope(Job() + Dispatchers.IO).launch {
            log("start ")

            launch {
                delay(2000)
                log("cancel")
                job.cancel()
            }
            job = launch {
                runCatching {
                    doSmth()
                }.onFailure {
                    if (it is CancellationException) throw it else log("onFailure: $it")
                }
            }
            job.join()
            latch.countDown()
            log("end ")
        }

    }
}

private suspend fun doSmth() {
    delay(5000)
    log("fail")
    throw Exception()
}