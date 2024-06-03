import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking

private fun itemFlow(): Flow<Int> = flow {
    (1..3).forEach { id ->
        emit(id)
        delay(500)
    }
}

fun main() = runBlocking {
    val startTime = System.currentTimeMillis()
    val result = mutableListOf<Int>()
    val timePassed = { System.currentTimeMillis() - startTime }
    itemFlow()
        .onEach { item -> log("Emitting $item - ${timePassed()} ms") }
        .map { item ->
            delay(1000)
            log("Mapped $item - ${timePassed()} ms")
            item * 2
        }
        .collect { item -> result.add(item) }

    val endTime = System.currentTimeMillis()

    log("Result: $result")
    log("Time taken: ${endTime - startTime} ms")
}