package nl.dutchland.grove.temperatureandhumidity

import com.nhaarman.mockito_kotlin.mock
import nl.dutchland.grove.utility.FractionalPercentage
import nl.dutchland.grove.utility.temperature.Celcius
import nl.dutchland.grove.utility.time.Duration
import nl.dutchland.grove.utility.time.Millisecond
import org.iot.raspberry.grovepi.devices.GroveLightSensor
import org.iot.raspberry.grovepi.devices.GroveTemperatureAndHumiditySensor
import org.iot.raspberry.grovepi.devices.GroveTemperatureAndHumidityValue
import org.junit.Assert
import org.mockito.Mockito
import kotlin.test.Test

class GroveTemperatureAndHumiditySensorTest {
    @Test
    fun testGetTemperatureStatus() {
        // Arrange
        val inputTemperature = 20.0

        val groveSensor = Mockito.mock(GroveTemperatureAndHumiditySensor::class.java)
        Mockito.`when`(groveSensor.get()).thenReturn(GroveTemperatureAndHumidityValue(inputTemperature, 0.0))
        val sensor = nl.dutchland.grove.temperatureandhumidity.GroveTemperatureAndHumiditySensor(groveSensor)

        // Act
        val value = sensor.getStatus()

        // Assert
        Assert.assertEquals(inputTemperature, value.temperature.valueIn(Celcius), 0.001)
        Assert.assertTrue(value.timestamp.millisecondsSinceEpoch > 0)
    }
}