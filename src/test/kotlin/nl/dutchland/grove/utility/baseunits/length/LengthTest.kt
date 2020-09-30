package nl.dutchland.grove.utility.baseunits.length

import nl.dutchland.grove.utility.derivedunits.mechanical.area.Area
import nl.dutchland.grove.utility.derivedunits.mechanical.area.mm2
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
    fun testDivideDouble() {
        assertEquals(Length.of(1.5, Millimeter),
                Length.of(4.5, Millimeter) / 3.0)
    }

    @Test
    fun testDivideLength() {
        assertEquals(2.5,
                Length.of(5.0, Decimeter) / Length.of(20.0, Centimeter))
    }

    @Test
    fun testMultiplyDouble() {
        assertEquals(Length.of(90.0, Millimeter),
                Length.of(10.0, Millimeter) * 9.0)
    }

    @Test
    fun testMultiplyLength() {
        assertEquals(Area.of(200.0, mm2),
                Length.of(10.0, Millimeter) * Length.of(20.0, Millimeter))
    }

    @Test
    fun testEquals() {
        val someLength = Length.of(8.0, Millimeter)
        val sameLength = Length.of(8.0, Millimeter)

        assertAll(
                { assertTrue(someLength == sameLength) },
                { assertEquals(0, someLength.compareTo(sameLength)) },
                { assertEquals(someLength.hashCode(), sameLength.hashCode()) }
        )
    }

    @Test
    fun testGreaterThan() {
        val someLength = Length.of(8.0, Millimeter)
        val greaterLength = Length.of(9.0, Millimeter)

        assertAll(
                { assertTrue(greaterLength > someLength) },
                { assertTrue(someLength < greaterLength) }
        )
    }

    @Test
    fun testGreaterEquals() {
        val someLength = Length.of(8.0, Millimeter)
        val greaterLength = Length.of(9.0, Millimeter)

        assertAll(
                { assertTrue(greaterLength >= someLength) },
                { assertTrue(someLength <= greaterLength) }
        )
    }

    @Test
    fun testGreaterEquals_Equals() {
        val someLength = Length.of(8.0, Millimeter)
        val sameLength = Length.of(8.0, Millimeter)

        assertTrue(someLength == sameLength)
        assertTrue(someLength >= sameLength)
        assertTrue(someLength <= sameLength)
    }

    @Test
    fun testSum() {
        val lengths = listOf(
                Length.of(1.0, Meter),
                Length.of(2000.0, Millimeter),
                Length.of(30.0, Decimeter))

        assertEquals(Length.of(6.0, Meter), lengths.sum())
    }
}