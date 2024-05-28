package coroutines_internals.pluto_samples

import coroutines_internals.pluto_samples.Tag.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun main(): Unit = runBlocking {
    val positions = channelFlow {
        var time = 0f
        while (true) {
            send(time)
            time += 0.1f
            delay(100)
        }
    }
        .throttle(500)

    val tags: Flow<Tag> = channelFlow {
        delay(2000)
        var tag: Tag = Start
        var sentCounter = 0
        while (sentCounter < 2) {
            send(tag)
            tag = when (tag) {
                Start -> Middle
                Middle -> End
                End -> {
                    sentCounter++
                    Start
                }
            }
            delay(2200)
        }
    }

    val endPosition = 12F
    tags
        .combine(positions) { tag, position -> tag to position }
        .onEach { (tag, position) ->
            println(tag to position)
            if ((position > endPosition && tag == End) || position >= endPosition + 1)
                coroutineContext.cancelChildren()
        }
        .launchIn(this)

}

sealed interface Tag {
    object Start : Tag {
        override fun toString(): String = "Start"
    }

    object Middle : Tag {
        override fun toString(): String = "Middle"
    }

    object End : Tag {
        override fun toString(): String = "End"
    }
}


fun <T> Flow<T>.throttle(waitMillis: Int) = flow {
    coroutineScope {
        val context = coroutineContext
        var nextMillis = 0L
        var delayPost: Deferred<Unit>? = null
        collect {
            val current = System.currentTimeMillis()
            if (nextMillis < current) {
                nextMillis = current + waitMillis
                emit(it)
                delayPost?.cancel()
            } else {
                val delayNext = nextMillis
                delayPost?.cancel()
                delayPost = async(Dispatchers.Default) {
                    delay(nextMillis - current)
                    if (delayNext == nextMillis) {
                        nextMillis = System.currentTimeMillis() + waitMillis
                        withContext(context) {
                            emit(it)
                        }
                    }
                }
            }
        }
    }
}
