package nl.dutchland.grove.utility.time

import org.junit.Assert
import org.junit.Test

class SecondTest {
    @Test
    fun testToSeconds() {
        Assert.assertEquals(1.0, Second.toSeconds(1.0), 0.001)
    }

    @Test
    fun testFromSeconds() {
        Assert.assertEquals(1.0, Second.fromSeconds(1.0), 0.001)
    }
}