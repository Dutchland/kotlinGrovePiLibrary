package nl.dutchland.grove.utility

import nl.dutchland.grove.utility.temperature.Celsius
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CelsiusTest {
    @Test
    fun test_ToKelvin() {
        val temperatureInKelvin = Celsius.toKelvin(20.0)
        assertEquals(293.15, temperatureInKelvin, 0.01)
    }

    @Test
    fun test_FromKelvin() {
        val temperatureinCelcius = Celsius.fromKelvin(200.0)
        assertEquals(-73.15, temperatureinCelcius, 0.01)
    }
}