package nl.dutchland.grove.temperatureandhumidity

import nl.dutchland.grove.utility.FractionalPercentage
import nl.dutchland.grove.utility.temperature.Celcius
import org.iot.raspberry.grovepi.devices.GroveTemperatureAndHumiditySensor
import org.iot.raspberry.grovepi.devices.GroveTemperatureAndHumidityValue
import org.junit.Assert
import org.mockito.Mockito
import kotlin.test.Test

class GroveTemperatureAndHumiditySensorTest {

    @Test
    fun testGetTemperatureStatus() {
        // Arrange
        val someTemperature = 20.0

        val groveSensor = Mockito.mock(GroveTemperatureAndHumiditySensor::class.java)
        Mockito.`when`(groveSensor.get()).thenReturn(GroveTemperatureAndHumidityValue(someTemperature, 0.0))
        val sensor = nl.dutchland.grove.temperatureandhumidity.GroveTemperatureAndHumiditySensor(groveSensor)

        // Act
        val value = sensor.getTemperature()

        // Assert
        Assert.assertEquals(someTemperature, value.temperature.valueIn(Celcius), 0.001)
        Assert.assertTrue(value.timestamp.millisecondsSinceEpoch > 0)
    }

    @Test
    fun testGetHumidityStatus() {
        // Arrange
        val relativeHumidityPercentage = 40.0

        val groveSensor = Mockito.mock(GroveTemperatureAndHumiditySensor::class.java)
        Mockito.`when`(groveSensor.get()).thenReturn(GroveTemperatureAndHumidityValue(0.0, relativeHumidityPercentage))
        val sensor = nl.dutchland.grove.temperatureandhumidity.GroveTemperatureAndHumiditySensor(groveSensor)

        // Act
        val value = sensor.getHumidity()

        // Assert
        Assert.assertEquals(FractionalPercentage.ofPercentage(relativeHumidityPercentage), value.humidity.relativeHumidity)
        Assert.assertTrue(value.timestamp.millisecondsSinceEpoch > 0)
    }

    @Test
    fun testGetTemperatureAndHumidityStatus() {
        // Arrange
        val someRelativeHumidityPercentage = 40.0
        val someTemperature = 20.0

        val groveSensor = Mockito.mock(GroveTemperatureAndHumiditySensor::class.java)
        Mockito.`when`(groveSensor.get()).thenReturn(GroveTemperatureAndHumidityValue(someTemperature, someRelativeHumidityPercentage))
        val sensor = nl.dutchland.grove.temperatureandhumidity.GroveTemperatureAndHumiditySensor(groveSensor)

        // Act
        val value = sensor.getTemperatureAndHumidityMeasurement()

        // Assert
        Assert.assertEquals(someTemperature, value.temperature.valueIn(Celcius), 0.001)
        Assert.assertEquals(FractionalPercentage.ofPercentage(someRelativeHumidityPercentage), value.humidity.relativeHumidity)
        Assert.assertTrue(value.timeStamp.millisecondsSinceEpoch > 0)
    }
}