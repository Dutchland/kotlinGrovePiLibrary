package nl.dutchland.grove.utility.weight

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class WeightTest {
    @Test
    fun testPlus() {
        val tenKg = Weight.of(10.0, Kilogram)
        val oneKg = Weight.of(1.0, Kilogram)

        val elevenKg = Weight.of(11.0, Kilogram)
        val sum = tenKg + oneKg

        assertEquals(elevenKg, sum)
    }

    @Test
    fun testMinus() {
        val tenKg = Weight.of(10.0, Kilogram)
        val oneKg = Weight.of(1.0, Kilogram)

        assertEquals(Weight.of(9.0, Kilogram), tenKg - oneKg)
    }
}