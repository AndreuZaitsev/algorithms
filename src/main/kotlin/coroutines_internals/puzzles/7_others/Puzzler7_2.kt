package `5_AsyncAwait`

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import log
import java.lang.Thread.sleep


fun main() {
    val startTime = System.currentTimeMillis()
    runBlocking {
        repeat(10) {
            launch {
                sleep(500)
                log("$it ${System.currentTimeMillis() - startTime}ms")
            }
        }
    }
}
