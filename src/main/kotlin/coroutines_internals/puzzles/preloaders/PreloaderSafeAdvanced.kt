package coroutines_internals.puzzles.preloaders

import `5_AsyncAwait`.channels
import `5_AsyncAwait`.onDemands
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import log

class PreloaderSafeAdvanced {

    suspend fun start(): Result<Unit> = coroutineScope {
        val results: List<Result<List<String>>> = awaitAll(
            async { preloadChannels() },
            async { preloadOnDemands() },
        )
        log("results: $results")
        if (results.all { it.isSuccess }) {
            Result.success(Unit)
        } else {
            val e = results.firstNotNullOfOrNull { it.exceptionOrNull() }
                ?: Exception("Warming up failed")
            Result.failure(e)
        }
    }

    private suspend fun preloadChannels(): Result<List<String>> = withContext(Dispatchers.IO) {
        runCatching {
            log("Preloading channels...")
            delay(1000)
            throw Exception("Failed to preload channels")
            channels
        }
    }

    private suspend fun preloadOnDemands(): Result<List<String>> = withContext(Dispatchers.IO) {
        runCatching {
            log("Preloading on demands...")
            delay(2000)
            onDemands
        }
    }
}