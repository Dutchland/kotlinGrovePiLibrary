package nl.dutchland.grove.utility.baseunits.length

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class CentimeterTest {
    @Test
    fun testCm() {
        assertAll(
                { assertEquals(0.01, Centimeter.toMeter(1.0)) },
                { assertEquals(100.0, Centimeter.fromMeter(1.0)) }
        )
    }
}