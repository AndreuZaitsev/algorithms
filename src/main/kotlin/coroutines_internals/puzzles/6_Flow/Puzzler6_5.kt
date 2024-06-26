package `6_Flow`

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import log

private fun itemFlow(): Flow<Int> = flow {
    (1..5).forEach { id ->
        emit(id)
        delay(50)
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
fun main() = runBlocking {
    val startTime = System.currentTimeMillis()
    val result = mutableListOf<Int>()

    itemFlow()
        .flatMapMerge { item ->
            flow {
                withContext(Dispatchers.IO) {
                    delay(100)
                    emit(item * 2)
                }
            }
        }
        .collect { item -> result.add(item) }

    val endTime = System.currentTimeMillis()

    log("Result: $result")
    log("Time taken: ${endTime - startTime} ms")
}