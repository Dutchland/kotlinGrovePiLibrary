package nl.dutchland.grove.temperatureandhumidity

import com.nhaarman.mockito_kotlin.*
import nl.dutchland.grove.utility.Fraction
import nl.dutchland.grove.utility.RelativeHumidity
import nl.dutchland.grove.utility.temperature.Celsius
import nl.dutchland.grove.utility.temperature.Temperature
import org.iot.raspberry.grovepi.devices.GroveTemperatureAndHumiditySensor
import org.iot.raspberry.grovepi.devices.GroveTemperatureAndHumidityValue
import org.junit.jupiter.api.Assertions.*

import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.junit.jupiter.api.Test

class GroveTemperatureHumiditySensorTest {

    @Test
    fun testGetTemperatureStatus() {
        // Arrange
        val someTemperature = 20.0
        val mockSensor = mock(GroveTemperatureAndHumiditySensor::class.java)
        `when`(mockSensor.get()).thenReturn(GroveTemperatureAndHumidityValue(someTemperature, 0.0))

        val sensor = nl.dutchland.grove.temperatureandhumidity.GroveTemperatureHumiditySensor(mockSensor, {})

        // Act
        val value = sensor.getTemperature()

        // Assert
        assertEquals(someTemperature, value.temperature.valueIn(Celsius), 0.001)
        assertTrue(value.timestamp.millisecondsSinceEpoch > 0)
    }

    @Test
    fun testGetHumidityStatus() {
        // Arrange
        val relativeHumidityPercentage = 40.0

        val groveSensor = mock(GroveTemperatureAndHumiditySensor::class.java)
        `when`(groveSensor.get()).thenReturn(GroveTemperatureAndHumidityValue(0.0, relativeHumidityPercentage))
        val sensor = nl.dutchland.grove.temperatureandhumidity.GroveTemperatureHumiditySensor(groveSensor, {})

        // Act
        val value = sensor.getHumidity()

        // Assert
        assertEquals(Fraction.ofPercentage(relativeHumidityPercentage), value.humidity.relativeHumidity)
        assertTrue(value.timestamp.millisecondsSinceEpoch > 0)
    }

    @Test
    fun testGetTemperatureAndHumidityStatus() {
        // Arrange
        val someRelativeHumidityPercentage = 40.0
        val someTemperature = 20.0

        val groveSensor = mock(GroveTemperatureAndHumiditySensor::class.java)
        `when`(groveSensor.get()).thenReturn(GroveTemperatureAndHumidityValue(someTemperature, someRelativeHumidityPercentage))
        val sensor = nl.dutchland.grove.temperatureandhumidity.GroveTemperatureHumiditySensor(groveSensor, {})

        // Act
        val value = sensor.getTemperatureHumidity()

        // Assert
        assertEquals(someTemperature, value.temperature.valueIn(Celsius), 0.001)
        assertEquals(Fraction.ofPercentage(someRelativeHumidityPercentage), value.humidity.relativeHumidity)
        assertTrue(value.timeStamp.millisecondsSinceEpoch > 0)
    }

    @Test
    fun testSubscribeTemperature() {
        // Arrange
        val someTemperature = 20.0
        val someOtherTemperature = 30.0

        val groveSensor = mock(GroveTemperatureAndHumiditySensor::class.java)
        `when`(groveSensor.get()).thenReturn(GroveTemperatureAndHumidityValue(someTemperature, 0.0))
        val sensor = nl.dutchland.grove.temperatureandhumidity.GroveTemperatureHumiditySensor(groveSensor, {})

        val mockedListener = mock<TemperatureListener>()

        // Act
//        sensor.subscribeToTemperature(mockedListener)
        `when`(groveSensor.get()).thenReturn(GroveTemperatureAndHumidityValue(someOtherTemperature, 0.0))
        Thread.sleep(200)

        // Assert
        argumentCaptor<TemperatureMeasurement>().apply {
            verify(mockedListener, atLeast(2)).invoke(capture())
            assertEquals(Temperature.of(someTemperature, Celsius), firstValue.temperature)
            assertEquals(Temperature.of(someOtherTemperature, Celsius), allValues.last().temperature)
        }
    }

    @Test
    fun testSubscribeHumidity() {
        // Arrange
        val someHumidityPercentage = Fraction.ofPercentage(40.0)
        val someOtherHumidityPercentage = Fraction.ofPercentage(50.0)

        val groveSensor = mock(GroveTemperatureAndHumiditySensor::class.java)
        `when`(groveSensor.get()).thenReturn(GroveTemperatureAndHumidityValue(0.0, someHumidityPercentage.percentage))
        val sensor = nl.dutchland.grove.temperatureandhumidity.GroveTemperatureHumiditySensor(groveSensor, {})

        val mockedListener = mock<HumidityListener>()

        // Act
//        sensor.subscribeToHumidity(mockedListener)
        `when`(groveSensor.get()).thenReturn(GroveTemperatureAndHumidityValue(0.0, someOtherHumidityPercentage.percentage))
        Thread.sleep(200)

        // Assert
        argumentCaptor<HumidityMeasurement>().apply {
            verify(mockedListener, atLeast(2)).invoke(capture())
            assertEquals(RelativeHumidity(someHumidityPercentage), firstValue.humidity)
            assertEquals(RelativeHumidity(someOtherHumidityPercentage), allValues.last().humidity)
        }
    }

    @Test
    fun testSubscribeTemperatureHumidity() {
        // Arrange
        val someHumidityPercentage = Fraction.ofPercentage(40.0)
        val someOtherHumidityPercentage = Fraction.ofPercentage(50.0)

        val someTemperature = 20.0
        val someOtherTemperature = 30.0

        val groveSensor = mock(GroveTemperatureAndHumiditySensor::class.java)
        `when`(groveSensor.get()).thenReturn(GroveTemperatureAndHumidityValue(someTemperature, someHumidityPercentage.percentage))
        val sensor = nl.dutchland.grove.temperatureandhumidity.GroveTemperatureHumiditySensor(groveSensor, {})

        val mockedListener = mock<TemperatureHumidityListener>()

        // Act
//        sensor.subscribe(mockedListener)
        `when`(groveSensor.get()).thenReturn(GroveTemperatureAndHumidityValue(someOtherTemperature, someOtherHumidityPercentage.percentage))
        Thread.sleep(200)

        // Assert
        argumentCaptor<TemperatureHumidityMeasurement>().apply {
            verify(mockedListener, atLeast(2)).invoke(capture())

            assertEquals(RelativeHumidity(someHumidityPercentage), firstValue.humidity)
            assertEquals(RelativeHumidity(someOtherHumidityPercentage), allValues.last().humidity)

            assertEquals(Temperature.of(someTemperature, Celsius), firstValue.temperature)
            assertEquals(Temperature.of(someOtherTemperature, Celsius), allValues.last().temperature)
        }
    }
}