package nl.dutchland.grove.rotary

import nl.dutchland.grove.utility.FractionalPercentage
import org.iot.raspberry.grovepi.devices.GroveRotaryValue
import org.junit.Assert
import org.mockito.Mockito
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
        // Arrange
        val mockRotarySensor = mock(org.iot.raspberry.grovepi.devices.GroveRotarySensor::class.java)
        `when`(mockRotarySensor.get()).thenReturn(createGroveRotaryValue(angleInDegrees))

        val rotarySensor = GroveRotarySensor(mockRotarySensor)

        // Act
        val status = rotarySensor.getStatus()

        // Assert
        Assert.assertEquals(FractionalPercentage.ofFraction(expectedFraction), status)
    }

    @Test
    fun testOnChangedListener_StatusDoesNotChange() {
        // Arrange
        val mockeGroveRotarySensor = mock(org.iot.raspberry.grovepi.devices.GroveRotarySensor::class.java)
        Mockito.`when`(mockeGroveRotarySensor.get()).thenReturn(createGroveRotaryValue(300.0))
        val groveRotarySensor = GroveRotarySensor(mockeGroveRotarySensor)

        val fakeListener = com.nhaarman.mockito_kotlin.mock<RotaryChangedListener>()

        // Act
        groveRotarySensor.addStatusChangedListener(fakeListener)

        // Assert
        Mockito.verify(fakeListener).invoke(FractionalPercentage.ofPercentage(100.0))
    }

    @Test
    fun testOnChangedListener_StatusChanges() {
        // Arrange
        val mockeGroveRotarySensor = mock(org.iot.raspberry.grovepi.devices.GroveRotarySensor::class.java)
        Mockito.`when`(mockeGroveRotarySensor.get()).thenReturn(createGroveRotaryValue(300.0))
        val groveRotarySensor = GroveRotarySensor(mockeGroveRotarySensor)

        val fakeListener = com.nhaarman.mockito_kotlin.mock<RotaryChangedListener>()

        // Act
        groveRotarySensor.addStatusChangedListener(fakeListener)
        Mockito.`when`(mockeGroveRotarySensor.get()).thenReturn(createGroveRotaryValue(0.0))
        Thread.sleep(200)

        // Assert
        Mockito.verify(fakeListener).invoke(FractionalPercentage.ofPercentage(0.0))
    }

    private fun createGroveRotaryValue(angleInDegrees: Double): GroveRotaryValue {
        return GroveRotaryValue(1.0, 1.0, angleInDegrees)
    }
}