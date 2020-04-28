package nl.dutchland.grove.utility

import nl.dutchland.grove.utility.temperature.Kelvin
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class KelvinTest {
    @Test
    fun test_ToKelvin() {
        val temperatureInKelvin = Kelvin.toKelvin(20.0)
        assertEquals(20.0, temperatureInKelvin, 0.01)
    }

    @Test
    fun test_FromKelvin() {
        val temperatureinCelcius = Kelvin.fromKelvin(200.0)

        assertEquals(200.0, temperatureinCelcius, 0.01)
    }
}