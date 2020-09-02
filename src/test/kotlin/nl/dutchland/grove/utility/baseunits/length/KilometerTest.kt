package nl.dutchland.grove.utility.baseunits.length

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class KilometerTest {
    @Test
    fun testKm() {
        assertAll(
                { assertEquals(1000.0, Kilometer.toMeter(1.0)) },
                { assertEquals(0.001, Kilometer.fromMeter(1.0)) }
        )
    }
}