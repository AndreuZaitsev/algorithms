package coroutines_internals.pluto_samples

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import kotlin.system.measureTimeMillis
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.rx2.asFlow
import log

fun main() {
    val usecase = UseCase()
    fun lib() = usecase.getCurrentPlayerLibrary()

    Thread.sleep(1000)

    val time1 = measureTimeMillis { log(lib()) }
    log("$time1 - time 1")
    val time2 = measureTimeMillis { log(lib()) }
    log("$time2 - time 2")
}

class UseCase {

    private val library: StateFlow<String?> = getPlayerLibrary()
        .toObservable()
        .asFlow()
        .catch {
            log("Error occurred while listening to avia feature")
            emit("EXO")
        }
        .onStart { log("warming up") }
        .stateIn(CoroutineScope(Job()), SharingStarted.Eagerly, initialValue = null)

    fun getCurrentPlayerLibrary(): String =
        runBlocking {
            library.value ?: library.onStart { log("fetch since since nullable still") }.filterNotNull().first()
        }

    private fun getPlayerLibrary(): Single<String> =
        Observable.fromCallable {
            log("callable")
            "AVIA"
        }
            .delay(3000, TimeUnit.MILLISECONDS)
            .single("EXO")
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
}