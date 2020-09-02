package nl.dutchland.grove.utility.baseunits.length

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class DecimeterTest {
    @Test
    fun testDm() {
        assertAll(
                { assertEquals(0.1, Decimeter.toMeter(1.0)) },
                { assertEquals(10.0, Decimeter.fromMeter(1.0)) }
        )
    }
}