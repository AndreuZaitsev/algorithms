package `5_AsyncAwait`

import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import log
import java.lang.Thread.sleep


fun main() = runBlocking {
    val startTime = System.currentTimeMillis()
    val job1 = launch {
        log("a")
        delay(1000)
        log("b")
    }
    val job2 = launch {
        log("c")
        sleep(1000)
        log("d")
    }
    val job3 = launch {
        log("e")
        delay(2000)
        log("f")
    }

    joinAll(job1, job2, job3)
    log("end, passed time = ${System.currentTimeMillis() - startTime}ms")
}