package nl.dutchland.grove.utility

import org.junit.Assert
import org.junit.Test
import testutility.ExceptionAssert

class KelvinTest {
    @Test
    fun test_ToKelvin() {
        val kelvin = Kelvin()
        val temperatureInKelvin = kelvin.toKelvin(20.0)

        Assert.assertEquals(20.0, temperatureInKelvin, 0.01)
    }

    @Test
    fun test_FromKelvin() {
        val kelvin = Kelvin()
        val temperatureinCelcius = kelvin.fromKelvin(200.0)

        Assert.assertEquals(200.0, temperatureinCelcius, 0.01)
    }

    @Test
    fun test_validate_Invalid() {
        val kelvin = Kelvin()

        ExceptionAssert.assertThrows { kelvin.validate(-1.0) }
                .assertExactExceptionType(InvalidTemperatureException::class)
                .assertExceptionMessage("Invalid temperature: -1.0. Minimal value is 0.0 Kelvin")
    }

    @Test
    fun test_validate_AbsoluteZero() {
        val kelvin = Kelvin()
        ExceptionAssert.assertNotThrows { kelvin.validate( 0.0 ) }
    }
}