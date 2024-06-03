package `6_Flow`

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import log

private fun itemFlow(): Flow<Int> = flow {
    (1..5).forEach { id ->
        emit(id)
        delay(50)
    }
}

fun Flow<Int>.timed(delay: Long): Flow<Pair<Int, Long>> = flow {
    var startTime = 0L

    buffer(1, BufferOverflow.DROP_OLDEST).collect { item ->
        if (startTime == 0L) {
            startTime = System.currentTimeMillis()
        }

        emit(item to (System.currentTimeMillis() - startTime))
        delay(delay)
    }
}

fun main(): Unit = runBlocking {
    val sharedFlow = itemFlow()
        .shareIn(this, SharingStarted.Lazily, 0)
//        .shareIn(this, SharingStarted.Eagerly, 0)

    launch {
        sharedFlow.collect {
            log("first collector: $it")
        }
    }

    launch {
        sharedFlow
            .timed(1000)
            .collect {
                log("second collector: $it")
            }
    }
}