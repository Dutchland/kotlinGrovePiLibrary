package nl.dutchland.grove.utility.time

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MillisecondTest {
    @Test
    fun testToSeconds() {
        assertEquals(1.0, Millisecond.toSeconds(1_000.0), 0.001)
    }

    @Test
    fun testFromSeconds() {
        assertEquals(1_000.0, Millisecond.fromSeconds(1.0), 0.001)
    }
}