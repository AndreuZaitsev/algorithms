package coroutines_internals

import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import log

class Producer {

    val resultsFlow = MutableSharedFlow<String>() // Example type, replace with your actual type

    private var handler: suspend (String) -> Unit = {

    }

    suspend fun getValue() {
        delay(2000)
        suspendCoroutine<Unit> {
            it.resume(Unit)
            log("asked device")
        }
    }

    suspend fun subscribeToResults() {
        delay(1000)
        suspendCoroutine<Unit> {
            launchCallback()
            handler = {
                log("handle answer")
                resultsFlow.emit(it)
            }
            it.resume(Unit)
            log("subscribed")
        }
    }

    private fun launchCallback() {
        GlobalScope.launch {
            log("launch callback")
            delay(3000)
            handler.invoke("HELLO")
        }
    }
}

class Consumer(private val producer: Producer) {

    private fun getValue(): Flow<String> = producer.resultsFlow

    suspend fun bind() {
        producer.subscribeToResults()
    }

    suspend fun getAnswer(): String {
        producer.getValue()
        return producer.resultsFlow.firstOrNull().orEmpty()
    }
}

fun main() {
    val producer = Producer()
    val consumer = Consumer(producer)

    runBlocking {
        consumer.bind()
        consumer.getAnswer().also { log(it) }
    }
}
