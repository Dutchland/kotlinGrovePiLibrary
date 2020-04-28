package nl.dutchland.grove.utility.length

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

class InchTest {

    @Test
    fun testInchesToMillimeter() {
        val singleInch = Length.of(1.0, Inch)
        assertEquals(25.2, singleInch.valueIn(Millimeter))
    }
}