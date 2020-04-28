package nl.dutchland.grove.utility.time

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class MinuteTest {
    @Test
    fun testToSecond() {
        val tenMinutes = 10.0
        val inSeconds = Minute.toSeconds(tenMinutes)

        assertEquals(600.0, inSeconds, 0.001)
    }

    @Test
    fun testFromSeconds() {
        val hundredEightySeconds = 180.0
        val inMinutes = Minute.fromSeconds(hundredEightySeconds)

        assertEquals(3.0, inMinutes, 0.001)
    }
}