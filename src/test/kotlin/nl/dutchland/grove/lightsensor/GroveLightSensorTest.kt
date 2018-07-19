package nl.dutchland.grove.lightsensor

import com.nhaarman.mockito_kotlin.mock
import nl.dutchland.grove.utility.FractionalPercentage
import nl.dutchland.grove.utility.Interval
import org.iot.raspberry.grovepi.devices.GroveLightSensor
import org.junit.Assert
import org.mockito.Mockito.*
import kotlin.test.Test

class GroveLightSensorTest {
    private val MAX_SENSOR_VALUE = 1023.0

    @Test
    fun testGetStatus() {
        testGetStatus(0.0, 0.0)
        testGetStatus(MAX_SENSOR_VALUE, 1.0) // Max value
        testGetStatus(MAX_SENSOR_VALUE / 2.0, 0.5)
        testGetStatus(-1.0, 0.0) // Negative value
        testGetStatus(2 * MAX_SENSOR_VALUE, 1.0) // Larger than max value
    }

    private fun testGetStatus(input: Double, expectedOutput: Double) {
        // Arrange
        val groveSensor = mock(GroveLightSensor::class.java)
        `when`(groveSensor.get()).thenReturn(input)
        val sensor = nl.dutchland.grove.lightsensor.GroveLightSensor(groveSensor)

        // Act
        val value = sensor.getStatus()

        // Assert
        Assert.assertEquals(expectedOutput, value.fraction, 0.001)
    }

    @Test
    fun testAddingAListener() {
        // Arrange
        val groveSensor = mock(GroveLightSensor::class.java)
        `when`(groveSensor.get()).thenReturn(0.0)
        val sensor = nl.dutchland.grove.lightsensor.GroveLightSensor(groveSensor)

        val mockedListener = mock<LightSensorValueListener>()

        // Act
        sensor.subscribe(mockedListener, Interval.inMilliseconds(100))
        `when`(groveSensor.get()).thenReturn(MAX_SENSOR_VALUE)
        Thread.sleep(101)

        // Assert
        verify(mockedListener).invoke(FractionalPercentage.ofFraction(0.0))
        verify(mockedListener).invoke(FractionalPercentage.ofFraction(1.0))
    }
}