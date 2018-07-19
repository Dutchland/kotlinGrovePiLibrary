package nl.dutchland.grove.utility

import org.junit.Assert
import testutility.ExceptionAssert
import testutility.ExceptionThrower
import kotlin.test.Test

class IntervalTest {
    @Test
    fun testNegativeInterval() {
        val creatingNegativeInterval: ExceptionThrower = { Interval.inMilliseconds(-1)}
        ExceptionAssert.assertThrows { creatingNegativeInterval.invoke() }
                .assertExactExceptionType(InvalidIntervalException::class)
                .assertExceptionMessage("Interval cannot be negative: -1")
    }

    @Test
    fun testIntervalInMilliseconds() {
        val interval = Interval.inMilliseconds(999)
        Assert.assertEquals(999, interval.inMilliseconds)
    }
}