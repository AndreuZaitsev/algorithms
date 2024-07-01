package coroutines_internals.puzzles

import coroutines_internals.puzzles.preloaders.Preloader
import coroutines_internals.puzzles.preloaders.PreloaderSafe
import coroutines_internals.puzzles.preloaders.PreloaderSafeAdvanced
import kotlinx.coroutines.runBlocking
import log
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

@OptIn(ExperimentalTime::class)
fun main() = runBlocking {
    val p1 = Preloader()
    val p2 = PreloaderSafe()
    val p3 = PreloaderSafeAdvanced()
    val executionTimeMs = measureTime {
        p3.start()
    }.inWholeMilliseconds

    log("Execution time: $executionTimeMs ms")
}