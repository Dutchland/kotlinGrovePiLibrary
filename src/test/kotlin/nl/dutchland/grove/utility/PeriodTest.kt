package nl.dutchland.grove.utility

import com.nhaarman.mockito_kotlin.mock
import nl.dutchland.grove.utility.baseunits.time.Period
import nl.dutchland.grove.utility.baseunits.time.InvalidIntervalException
import nl.dutchland.grove.utility.baseunits.time.Time
import testutility.ExceptionAssert
import testutility.ExceptionThrower
import org.junit.jupiter.api.Test

class PeriodTest {
    @Test
    fun testNegativeInterval() {
        val fakeTimeScale = mock<Time.Unit>()

        val creatingNegativeDuration: ExceptionThrower = { Period.of(-1.0, fakeTimeScale)}
        ExceptionAssert.assertThrows { creatingNegativeDuration.invoke() }
                .assertExactExceptionType(InvalidIntervalException::class)
                .assertExceptionMessage("Period cannot be negative")
    }
}