package nl.dutchland.grove.utility

import org.junit.Assert
import org.junit.Test
import testutility.ExceptionAssert

class CelciusTest {
    @Test
    fun test_ToKelvin() {
        val celcius = Celcius()
        val temperatureInKelvin = celcius.toKelvin(20.0)

        Assert.assertEquals(293.15, temperatureInKelvin, 0.01)
    }

    @Test
    fun test_FromKelvin() {
        val celcius = Celcius()
        val temperatureinCelcius = celcius.fromKelvin(200.0)

        Assert.assertEquals(-73.15, temperatureinCelcius, 0.01)
    }

    @Test
    fun test_validate_Invalid() {
        val celcius = Celcius()

        ExceptionAssert.assertThrows { celcius.validate(-300.0) }
                .assertExactExceptionType(InvalidTemperatureException::class)
                .assertExceptionMessage("Invalid temperature: -300.0. Minimal value is -273.15 Celcius")
    }

    @Test
    fun test_validate_AbsoluteZero() {
        val celcius = Celcius()
        ExceptionAssert.assertNotThrows { celcius.validate( -273.15 ) }
    }
}