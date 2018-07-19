package nl.dutchland.grove.utility

import com.nhaarman.mockito_kotlin.mock
import nl.dutchland.grove.utility.time.Duration
import nl.dutchland.grove.utility.time.InvalidIntervalException
import testutility.ExceptionAssert
import testutility.ExceptionThrower
import kotlin.test.Test

class DurationTest {
    @Test
    fun testNegativeInterval() {
        val fakeTimeScale = mock<Duration.TimeScale>()

        val creatingNegativeDuration: ExceptionThrower = { Duration.of(-1.0, fakeTimeScale)}
        ExceptionAssert.assertThrows { creatingNegativeDuration.invoke() }
                .assertExactExceptionType(InvalidIntervalException::class)
                .assertExceptionMessage("Duration cannot be negative")
    }
}