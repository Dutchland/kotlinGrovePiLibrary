package nl.dutchland.grove.utility.baseunits.length

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.provider.Arguments

internal class MeterTest {
    @Test
    fun testMeter() {
        assertAll(
                { assertEquals(1.0, Meter.toMeter(1.0)) },
                { assertEquals(1.0, Meter.fromMeter(1.0)) }
        )
    }
}