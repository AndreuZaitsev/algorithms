package `5_AsyncAwait`

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import log


fun main() {
    val startTime = System.currentTimeMillis()
    runBlocking {
        launch {
            repeat(10) {
                delay(500)
                log("$it ${System.currentTimeMillis() - startTime}ms")
            }
        }
    }
}