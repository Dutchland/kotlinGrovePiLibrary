package nl.dutchland.grove.utility.time

import org.junit.Assert
import org.junit.Test

class MillisecondTest {
    @Test
    fun testToSeconds() {
        Assert.assertEquals(1.0, Millisecond.toSeconds(1_000.0), 0.001)
    }

    @Test
    fun testFromSeconds() {
        Assert.assertEquals(1_000.0, Millisecond.fromSeconds(1.0), 0.001)
    }
}