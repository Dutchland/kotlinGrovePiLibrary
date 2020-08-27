package nl.dutchland.grove.utility.mass

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class MassTest {
    @Test
    fun testPlus() {
        val tenKg = Mass.of(10.0, Kilogram)
        val oneKg = Mass.of(1.0, Kilogram)

        val elevenKg = Mass.of(11.0, Kilogram)
        val sum = tenKg + oneKg

        assertEquals(elevenKg, sum)
    }

    @Test
    fun testMinus() {
        val tenKg = Mass.of(10.0, Kilogram)
        val oneKg = Mass.of(1.0, Kilogram)

        assertEquals(Mass.of(9.0, Kilogram), tenKg - oneKg)
    }
}