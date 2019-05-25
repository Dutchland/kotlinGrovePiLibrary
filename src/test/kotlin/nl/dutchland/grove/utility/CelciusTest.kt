package nl.dutchland.grove.utility

import nl.dutchland.grove.utility.temperature.Celcius
import nl.dutchland.grove.utility.temperature.InvalidTemperatureException
import org.junit.Assert
import org.junit.Test
import testutility.ExceptionAssert

class CelciusTest {
    @Test
    fun test_ToKelvin() {
        val temperatureInKelvin = Celcius.toKelvin(20.0)

        Assert.assertEquals(293.15, temperatureInKelvin, 0.01)
    }

    @Test
    fun test_FromKelvin() {
        val temperatureinCelcius = Celcius.fromKelvin(200.0)

        Assert.assertEquals(-73.15, temperatureinCelcius, 0.01)
    }
}