package coroutines_internals.puzzles.preloaders

import `5_AsyncAwait`.channels
import `5_AsyncAwait`.onDemands
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext
import log

class Preloader {

    suspend fun start(): Unit = coroutineScope {
        val def1 = async { preloadChannels() }
        val def2 = async { preloadOnDemands() }

        runCatching { log("result1: ${def1.await()}") }
        runCatching { log("result2: ${def2.await()}") }
    }

    private suspend fun preloadChannels(): List<String> = withContext(Dispatchers.IO) {
        log("Preloading channels...")
        delay(1000)
        throw Exception("Failed to preload channels")
        channels
    }

    private suspend fun preloadOnDemands(): List<String> = withContext(Dispatchers.IO) {
        log("Preloading on demands...")
        delay(2000)
        onDemands
    }
}