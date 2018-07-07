package nl.dutchland.grove.led

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import nl.dutchland.grove.utility.FractionalPercentage
import org.junit.Test
import org.junit.runner.RunWith

class GroveLedTest {
    @Test
    fun testTurnOn() {
        // Arrange
        val mockedLed = mock<org.iot.raspberry.grovepi.devices.GroveLed>()
        val groveLed = GroveLed(mockedLed)

        // Act
        groveLed.turnOn()

        // Assert
        verify(mockedLed).set(true)
    }

    @Test
    fun testTurnOff() {
        // Arrange
        val mockedLed = mock<org.iot.raspberry.grovepi.devices.GroveLed>()
        val groveLed = GroveLed(mockedLed)

        // Act
        groveLed.turnOff()

        // Assert
        verify(mockedLed).set(false)
    }

    @Test
    fun testTurnOnWithPercentage() {
        testTurnOnWithPercentage(100.0, org.iot.raspberry.grovepi.devices.GroveLed.MAX_BRIGTHNESS) // Max value
        testTurnOnWithPercentage(0.0, 0) // Zero
        testTurnOnWithPercentage(0.01, 1) // Round up
    }

    private fun testTurnOnWithPercentage(percentage: Double, expectedBrightnessNumber: Int) {
        // Arrange
        val mockedLed = mock<org.iot.raspberry.grovepi.devices.GroveLed>()
        val groveLed = GroveLed(mockedLed)

        // Act
        groveLed.turnOn(FractionalPercentage.ofPercentage(percentage))

        // Assert
        verify(mockedLed).set(expectedBrightnessNumber)
    }
}