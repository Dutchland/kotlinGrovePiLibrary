package nl.dutchland.grove.utility.length

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class LengthTest {
    @Test
    fun testPlus() {
        assertEquals(Length.of(15.0, Millimeter),
                Length.of(10.0, Millimeter) + Length.of(5.0, Millimeter))
    }

    @Test
    fun testMinus() {
        assertEquals(Length.of(7.0, Millimeter),
                Length.of(10.0, Millimeter) - Length.of(3.0, Millimeter))
    }

    @Test
    fun testEquals() {
        val someLength = Length.of(8.0, Millimeter)
        val sameLength = Length.of(8.0, Millimeter)

        assertTrue(someLength == sameLength)
    }

    @Test
    fun testGreaterThan() {
        val someLength = Length.of(8.0, Millimeter)
        val greaterLength = Length.of(9.0, Millimeter)

        assertTrue(greaterLength > someLength)
        assertTrue(someLength < greaterLength)
    }

    @Test
    fun testGreaterEquals() {
        val someLength = Length.of(8.0, Millimeter)
        val greaterLength = Length.of(9.0, Millimeter)

        assertTrue(greaterLength >= someLength)
        assertTrue(someLength <= greaterLength)
    }

    @Test
    fun testGreaterEquals_Equals() {
        val someLength = Length.of(8.0, Millimeter)
        val sameLength = Length.of(8.0, Millimeter)

        assertTrue(someLength == sameLength)
        assertTrue(someLength >= sameLength)
        assertTrue(someLength <= sameLength)
    }
}