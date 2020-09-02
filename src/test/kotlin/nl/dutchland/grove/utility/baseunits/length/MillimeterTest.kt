package nl.dutchland.grove.utility.baseunits.length

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class MillimeterTest {
    @Test
    fun testMm() {
        assertAll(
                { assertEquals(0.001, Millimeter.toMeter(1.0)) },
                { assertEquals(1000.0, Millimeter.fromMeter(1.0)) }
        )
    }
}