package nl.dutchland.grove.lightsensor

import org.iot.raspberry.grovepi.devices.GroveLightSensor
import org.junit.Assert
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import kotlin.test.Test

class GroveLightSensorTest {
    @Test
    fun testGetStatus() {
        testGetStatus(0.0, 0.0)
        testGetStatus(1023.0, 1.0) // Max value
        testGetStatus(512.5, 0.5)
        testGetStatus(-1.0, 0.0) // Negative value
        testGetStatus(2000.0, 1.0) // Larger than max value
    }

    private fun testGetStatus(input: Double, expectedOutput: Double) {
        // Arrange
        val groveSensor = mock(GroveLightSensor::class.java)
        `when`(groveSensor.get()).thenReturn(input)
        val sensor = nl.dutchland.grove.lightsensor.GroveLightSensor(groveSensor)

        // Act
        val value = sensor.getStatus()

        // Assert
        Assert.assertEquals(expectedOutput, value.fraction, 0.001)    }
}