package nl.dutchland.grove.utility

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class StandardUnitPrefixTest {
    @Test
    fun test() {
        assertEquals(100, StandardUnitPrefix.Hecto.factor)
    }
}