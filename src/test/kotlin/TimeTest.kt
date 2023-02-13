import org.junit.jupiter.api.Test
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.test.assertEquals

class TimeTest {
    // UTC time = Sat Jan 14 2023 09:53:20
    // Local Minsk time = Sat Jan 14 2023 12:53:20
    // Local Minsk time = Sat Jan 13 2023 12:53:20
    private val nowTimeMillis: Long = 1_673_690_000_000
    private val nowDate = Date(nowTimeMillis)

    @Test
    fun testDays() {
        run {
            val oneDayAgoDate = Date(nowTimeMillis - DAY)
            val days = oneDayAgoDate.calculateDiff(nowDate, TimeUnit.DAYS)
            assertEquals(1, days)
        }

        run {
            val twoDaysAgoDate = Date(nowTimeMillis - DAY * 2)
            val days = twoDaysAgoDate.calculateDiff(nowDate, TimeUnit.DAYS)
            assertEquals(2, days)
        }

        run {
            val zeroDaysAgoDate = Date(nowTimeMillis - DAY / 2)
            val days = zeroDaysAgoDate.calculateDiff(nowDate, TimeUnit.DAYS)
            assertEquals(0, days)
        }
    }

    @Test
    fun testDaysSimple() {
        // 7 days 4 hours 40 minutes 55 sec
        val currentDate = Date((DAY * 7) + (HOUR * 4) + (MIN * 40) + (SEC * 55))

        run {
            val oneDayAgoDate = Date(currentDate.time - DAY)
            val days = oneDayAgoDate.calculateDiff(currentDate, TimeUnit.DAYS)
            assertEquals(1, days)
        }

        run {
            val twoDaysAgoDate = Date(currentDate.time - DAY * 2)
            val days = twoDaysAgoDate.calculateDiff(currentDate, TimeUnit.DAYS)
            assertEquals(2, days)
        }

        run {
            val zeroDaysAgoDate = Date(currentDate.time - DAY / 2)
            val days = zeroDaysAgoDate.calculateDiff(currentDate, TimeUnit.DAYS)
            assertEquals(0, days)
        }
    }

    @Test
    fun testHoursSimple() {
        // 7 days 4 hours 40 minutes 55 sec
        val currentDate = Date((DAY * 7) + (HOUR * 4) + (MIN * 40) + (SEC * 55))

        run {
            val oneHourAgoDate = Date(currentDate.time - HOUR)
            val hours = oneHourAgoDate.calculateDiff2(currentDate, TimeUnit.HOURS)
            assertEquals(1, hours)
        }

        run {
            val twoHoursAgoDate = Date(currentDate.time - HOUR * 2)
            val hours = twoHoursAgoDate.calculateDiff2(currentDate, TimeUnit.HOURS)
            assertEquals(2, hours)
        }

        run {
            val zeroHoursAgoDate = Date(currentDate.time - HOUR / 2)
            val hours = zeroHoursAgoDate.calculateDiff2(currentDate, TimeUnit.HOURS)
            assertEquals(0, hours)
        }
    }

    @Test
    fun testMinutesSimple() {
        // 7 days 4 hours 40 minutes 55 sec
        val currentDate = Date((DAY * 7) + (HOUR * 4) + (MIN * 40) + (SEC * 55))

        run {
            val oneMinuteAgoDate = Date(currentDate.time - MIN)
            val days = oneMinuteAgoDate.calculateDiff2(currentDate, TimeUnit.MINUTES)
            assertEquals(1, days)
        }

        run {
            val twoMinutesAgoDate = Date(currentDate.time - MIN * 2)
            val days = twoMinutesAgoDate.calculateDiff2(currentDate, TimeUnit.MINUTES)
            assertEquals(2, days)
        }

        run {
            val zeroMinutesAgoDate = Date(currentDate.time - MIN / 2)
            val days = zeroMinutesAgoDate.calculateDiff2(currentDate, TimeUnit.MINUTES)
            assertEquals(0, days)
        }
    }

    @Test
    fun testSecondsSimple() {
        // 7 days 4 hours 40 minutes 55 sec
        val currentDate = Date((DAY * 7) + (HOUR * 4) + (MIN * 40) + (SEC * 55))

        run {
            val oneSecondAgoDate = Date(currentDate.time - SEC)
            val days = oneSecondAgoDate.calculateDiff2(currentDate, TimeUnit.SECONDS)
            assertEquals(1, days)
        }

        run {
            val twoSecondsAgoDate = Date(currentDate.time - SEC * 2)
            val days = twoSecondsAgoDate.calculateDiff2(currentDate, TimeUnit.SECONDS)
            assertEquals(2, days)
        }

        run {
            val zeroSecondsAgoDate = Date(currentDate.time - SEC / 2)
            val days = zeroSecondsAgoDate.calculateDiff2(currentDate, TimeUnit.SECONDS)
            assertEquals(0, days)
        }
    }

    @Test
    fun testTimeAgoString() {
        // 7 days 4 hours 40 minutes 55 sec
        val currentDate = Date((DAY * 7) + (HOUR * 4) + (MIN * 40) + (SEC * 55))

        // seconds
        val oneSecondAgoDate = Date(currentDate.time - SEC)
        val twoSecondAgoDate = Date(currentDate.time - SEC * 2)
        assertEquals("1 second ago", oneSecondAgoDate.humanizeDiff(currentDate))
        assertEquals("2 seconds ago", twoSecondAgoDate.humanizeDiff(currentDate))

        // minutes
        val oneMinuteAgoDate = Date(currentDate.time - MIN)
        val twoMinuteAgoDate = Date(currentDate.time - MIN * 2)
        assertEquals("1 minute ago", oneMinuteAgoDate.humanizeDiff(currentDate))
        assertEquals("2 minutes ago", twoMinuteAgoDate.humanizeDiff(currentDate))

        // hours
        val oneHourAgoDate = Date(currentDate.time - HOUR)
        val twoHourAgoDate = Date(currentDate.time - HOUR * 2)
        assertEquals("1 hour ago", oneHourAgoDate.humanizeDiff(currentDate))
        assertEquals("2 hours ago", twoHourAgoDate.humanizeDiff(currentDate))

        // days
        val oneDayAgoDate = Date(currentDate.time - DAY)
        val twoDayAgoDate = Date(currentDate.time - DAY * 2)
        assertEquals("1 day ago", oneDayAgoDate.humanizeDiff(currentDate))
        assertEquals("2 days ago", twoDayAgoDate.humanizeDiff(currentDate))
    }
}

const val SEC = 1000L
const val MIN = 60 * SEC
const val HOUR = 60 * MIN
const val DAY = 24 * HOUR

fun Date.calculateDiff2(fromDate: Date = Date(), timeUnit: TimeUnit): Long {
    val nowTimeMillis = fromDate.time
    val lastTimeMillis = this.time

    val elapsedTimeMillis = nowTimeMillis - lastTimeMillis

    val numberOfDays = { elapsedTimeMillis / DAY }
    val hours = { elapsedTimeMillis / HOUR }
    val mins = { elapsedTimeMillis / MIN }
    val secs = { elapsedTimeMillis / SEC }

    val result = when (timeUnit) {
        TimeUnit.DAYS -> numberOfDays()
        TimeUnit.HOURS -> hours()
        TimeUnit.MINUTES -> mins()
        TimeUnit.SECONDS -> secs()
        else -> elapsedTimeMillis
    }
    return result
}

fun Date.calculateDiff(fromDate: Date = Date(), timeUnit: TimeUnit = TimeUnit.MILLISECONDS): Long {
    val nowTimeMillis = fromDate.time
    val lastTimeMillis = this.time
    val elapsedTimeMillis = nowTimeMillis - lastTimeMillis

    return when (timeUnit) {
        TimeUnit.DAYS -> elapsedTimeMillis / DAY
        TimeUnit.HOURS -> elapsedTimeMillis / HOUR
        TimeUnit.MINUTES -> elapsedTimeMillis / MIN
        TimeUnit.SECONDS -> elapsedTimeMillis / SEC
        else -> elapsedTimeMillis
    }

    return (elapsedTimeMillis / DAY).takeIf { it > 0 }
        ?: (elapsedTimeMillis / HOUR).takeIf { it > 0 }
        ?: (elapsedTimeMillis / MIN).takeIf { it > 0 }
        ?: (elapsedTimeMillis / SEC).takeIf { it > 0 }
        ?: elapsedTimeMillis.coerceAtLeast(0)
}

fun Date.humanizeDiff(fromDate: Date = Date()): String {
    val nowTimeMillis = fromDate.time
    val lastTimeMillis = this.time
    val elapsedTimeMillis = nowTimeMillis - lastTimeMillis

    val multiplePostfix: (Long) -> String = { if (it > 1) "s" else "" }

    return (elapsedTimeMillis / DAY).takeIf { it > 0 }?.let { "$it day${multiplePostfix(it)} ago" }
        ?: (elapsedTimeMillis / HOUR).takeIf { it > 0 }?.let { "$it hour${multiplePostfix(it)} ago" }
        ?: (elapsedTimeMillis / MIN).takeIf { it > 0 }?.let { "$it minute${multiplePostfix(it)} ago" }
        ?: (elapsedTimeMillis / SEC).takeIf { it > 0 }?.let { "$it second${multiplePostfix(it)} ago" }
        ?: elapsedTimeMillis.coerceAtLeast(0).let { "$it milli${multiplePostfix(it)} ago" }
}