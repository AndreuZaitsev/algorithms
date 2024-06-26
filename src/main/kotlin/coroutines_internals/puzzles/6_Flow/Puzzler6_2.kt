package `6_Flow`

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull
import log
import kotlin.random.Random

fun numberFlow(): Flow<Int> = flow {
    repeat(3) {
        delay(100)
        emit(Random.nextInt(100))
    }
}

fun main(): Unit = runBlocking {
    withTimeoutOrNull(250) {
        numberFlow()
            .collect {
                delay(50)
                log(it.toString())
            }
    }
}