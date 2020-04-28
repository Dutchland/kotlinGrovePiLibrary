package nl.dutchland.grove.utility

import com.nhaarman.mockito_kotlin.mock
import nl.dutchland.grove.utility.time.Period
import nl.dutchland.grove.utility.time.InvalidIntervalException
import testutility.ExceptionAssert
import testutility.ExceptionThrower
import org.junit.jupiter.api.Test

class PeriodTest {
    @Test
    fun testNegativeInterval() {
        val fakeTimeScale = mock<Period.TimeScale>()

        val creatingNegativeDuration: ExceptionThrower = { Period.of(-1.0, fakeTimeScale)}
        ExceptionAssert.assertThrows { creatingNegativeDuration.invoke() }
                .assertExactExceptionType(InvalidIntervalException::class)
                .assertExceptionMessage("Period cannot be negative")
    }
}