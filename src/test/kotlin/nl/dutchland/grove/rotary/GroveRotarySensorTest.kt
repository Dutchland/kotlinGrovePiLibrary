package nl.dutchland.grove.rotary

import nl.dutchland.grove.utility.FractionalPercentage
import org.iot.raspberry.grovepi.devices.GroveRotaryValue
import org.junit.Assert
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import kotlin.test.Test

class GroveRotarySensorTest {
    @Test
    fun testGetValue() {
        testGetValue(300.0, 1.0) // Max angle
        testGetValue(0.0, 0.0) // Min angle
        testGetValue(150.0, 0.5)
        testGetValue(350.0, 1.0) // Invalid angle: bigger than max
        testGetValue(-100.0, 0.0) // Invalid angle: smaller than min
    }

    private fun testGetValue(angleInDegrees: Double, expectedFraction: Double) {
        // Given
        val mockRotarySensor = mock(org.iot.raspberry.grovepi.devices.GroveRotarySensor::class.java)
        `when`(mockRotarySensor.get()).thenReturn(GroveRotaryValue(1.0, 1.0, angleInDegrees))

        val rotarySensor = GroveRotarySensor(mockRotarySensor)

        // When
        val status = rotarySensor.getStatus()

        // Then
        Assert.assertEquals(FractionalPercentage.ofFraction(expectedFraction), status)
    }
}