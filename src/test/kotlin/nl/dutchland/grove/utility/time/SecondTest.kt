package nl.dutchland.grove.utility.time

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SecondTest {
    @Test
    fun testToSeconds() {
        assertEquals(1.0, Second.toSeconds(1.0), 0.001)
    }

    @Test
    fun testFromSeconds() {
        assertEquals(1.0, Second.fromSeconds(1.0), 0.001)
    }
}