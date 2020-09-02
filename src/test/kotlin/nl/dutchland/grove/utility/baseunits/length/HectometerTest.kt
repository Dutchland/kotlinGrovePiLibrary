package nl.dutchland.grove.utility.baseunits.length

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class HectometerTest {
    @Test
    fun testHm() {
        assertAll(
                { assertEquals(100.0, Hectometer.toMeter(1.0)) },
                { assertEquals(0.01, Hectometer.fromMeter(1.0)) }
        )
    }
}