package nl.dutchland.grove.utility

import nl.dutchland.grove.utility.temperature.InvalidTemperatureException
import nl.dutchland.grove.utility.temperature.Kelvin
import org.junit.Assert
import org.junit.Test
import testutility.ExceptionAssert

class KelvinTest {
    @Test
    fun test_ToKelvin() {
        val temperatureInKelvin = Kelvin.toKelvin(20.0)
        Assert.assertEquals(20.0, temperatureInKelvin, 0.01)
    }

    @Test
    fun test_FromKelvin() {
        val temperatureinCelcius = Kelvin.fromKelvin(200.0)

        Assert.assertEquals(200.0, temperatureinCelcius, 0.01)
    }

    @Test
    fun test_validate_Invalid() {
        ExceptionAssert.assertThrows { Kelvin.validate(-1.0) }
                .assertExactExceptionType(InvalidTemperatureException::class)
                .assertExceptionMessage("Invalid temperature: -1.0. Minimal value is 0.0 Kelvin")
    }

    @Test
    fun test_validate_AbsoluteZero() {
        ExceptionAssert.assertNotThrows { Kelvin.validate( 0.0 ) }
    }
}