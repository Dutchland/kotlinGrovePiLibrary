package nl.dutchland.grove.temperatureandhumidity

import com.nhaarman.mockito_kotlin.*
import nl.dutchland.grove.utility.FractionalPercentage
import nl.dutchland.grove.utility.temperature.Celcius
import nl.dutchland.grove.utility.temperature.Temperature
import nl.dutchland.grove.utility.time.Millisecond
import nl.dutchland.grove.utility.time.Period
import org.iot.raspberry.grovepi.devices.GroveTemperatureAndHumiditySensor
import org.iot.raspberry.grovepi.devices.GroveTemperatureAndHumidityValue
import org.junit.Assert.*

import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import kotlin.test.Test

class GroveTemperatureHumiditySensorTest {

    @Test
    fun testGetTemperatureStatus() {
        // Arrange
        val someTemperature = 20.0
        val mockSensor = mock(GroveTemperatureAndHumiditySensor::class.java)
        `when`(mockSensor.get()).thenReturn(GroveTemperatureAndHumidityValue(someTemperature, 0.0))

        val sensor = nl.dutchland.grove.temperatureandhumidity.GroveTemperatureHumiditySensor(mockSensor)

        // Act
        val value = sensor.getTemperature()

        // Assert
        assertEquals(someTemperature, value.temperature.valueIn(Celcius), 0.001)
        assertTrue(value.timestamp.millisecondsSinceEpoch > 0)
    }

    @Test
    fun testGetHumidityStatus() {
        // Arrange
        val relativeHumidityPercentage = 40.0

        val groveSensor = mock(GroveTemperatureAndHumiditySensor::class.java)
        `when`(groveSensor.get()).thenReturn(GroveTemperatureAndHumidityValue(0.0, relativeHumidityPercentage))
        val sensor = nl.dutchland.grove.temperatureandhumidity.GroveTemperatureHumiditySensor(groveSensor)

        // Act
        val value = sensor.getHumidity()

        // Assert
        assertEquals(FractionalPercentage.ofPercentage(relativeHumidityPercentage), value.humidity.relativeHumidity)
        assertTrue(value.timestamp.millisecondsSinceEpoch > 0)
    }

    @Test
    fun testGetTemperatureAndHumidityStatus() {
        // Arrange
        val someRelativeHumidityPercentage = 40.0
        val someTemperature = 20.0

        val groveSensor = mock(GroveTemperatureAndHumiditySensor::class.java)
        `when`(groveSensor.get()).thenReturn(GroveTemperatureAndHumidityValue(someTemperature, someRelativeHumidityPercentage))
        val sensor = nl.dutchland.grove.temperatureandhumidity.GroveTemperatureHumiditySensor(groveSensor)

        // Act
        val value = sensor.getTemperatureHumidity()

        // Assert
        assertEquals(someTemperature, value.temperature.valueIn(Celcius), 0.001)
        assertEquals(FractionalPercentage.ofPercentage(someRelativeHumidityPercentage), value.humidity.relativeHumidity)
        assertTrue(value.timeStamp.millisecondsSinceEpoch > 0)
    }

    @Test
    fun testSubscribeTemperature() {
        // Arrange
        val someTemperature = 20.0
        val someOtherTemperature = 30.0

        val groveSensor = mock(GroveTemperatureAndHumiditySensor::class.java)
        `when`(groveSensor.get()).thenReturn(GroveTemperatureAndHumidityValue(someTemperature, 0.0))
        val sensor = nl.dutchland.grove.temperatureandhumidity.GroveTemperatureHumiditySensor(groveSensor)

        val mockedListener = mock<TemperatureListener>()

        // Act
        sensor.subscribeToTemperature(mockedListener, Period.of(100.0, Millisecond))
        `when`(groveSensor.get()).thenReturn(GroveTemperatureAndHumidityValue(someOtherTemperature, 0.0))
        Thread.sleep(150)

        // Assert
        argumentCaptor<TemperatureMeasurement>().apply {
            verify(mockedListener, times(2)).invoke(capture())
            assertEquals(Temperature.of(someTemperature, Celcius), firstValue.temperature)
            assertEquals(Temperature.of(someOtherTemperature, Celcius), secondValue.temperature)
        }
    }
}