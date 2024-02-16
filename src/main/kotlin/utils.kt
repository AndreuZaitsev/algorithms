import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import java.util.concurrent.CountDownLatch

fun log(str: String) {
    val threadName = Thread.currentThread().name
    val space = " ".repeat(30 - threadName.length)
    println("""
        [$threadName]$space$str 
    """.trimIndent())
}

fun traceBlocking(action: (CountDownLatch) -> Unit) {
    val dagger = Typography.dagger.toString().repeat(3)
    val latch = CountDownLatch(1)
    println("""
        -------------
        $dagger START
        -------------
    """.trimIndent())
    action(latch)
    latch.await()
    println("""
        -------------
        $dagger END
    """.trimIndent())
}