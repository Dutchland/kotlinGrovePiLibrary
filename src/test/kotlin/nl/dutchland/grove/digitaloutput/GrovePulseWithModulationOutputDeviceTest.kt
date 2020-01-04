package nl.dutchland.grove.digitaloutput

import nl.dutchland.grove.utility.Fraction
import org.iot.raspberry.grovepi.devices.GroveLed
import org.junit.Test
import org.mockito.Mockito

class GrovePulseWithModulationOutputDeviceTest {
    @Test
    fun testTurnOn() {
        // Arrange
        val mockedLed = Mockito.mock(GroveLed::class.java)
        val device = SomePulseWithModulationDevice(mockedLed)

        // Act
        device.turnOn()

        // Assert
        Mockito.verify(mockedLed).set(true)
    }

    @Test
    fun testTurnOff() {
        // Arrange
        val mockedLed = Mockito.mock(GroveLed::class.java)
        val groveLed = SomePulseWithModulationDevice(mockedLed)

        // Act
        groveLed.turnOff()

        // Assert
        Mockito.verify(mockedLed).set(false)
    }

    @Test
    fun testTurnOnWithPercentage() {
        testTurnOnWithPercentage(100.0, org.iot.raspberry.grovepi.devices.GroveLed.MAX_BRIGTHNESS) // Max value
        testTurnOnWithPercentage(0.0, 0) // Zero
        testTurnOnWithPercentage(0.01, 1) // Round up
    }

    private fun testTurnOnWithPercentage(percentage: Double, groveNumber: Int) {
        // Arrange
        val mockedLed = Mockito.mock(GroveLed::class.java)
        val device = SomePulseWithModulationDevice(mockedLed)

        // Act
        device.turnOn(Fraction.ofPercentage(percentage))

        // Assert
        Mockito.verify(mockedLed).set(groveNumber)
    }

    private class SomePulseWithModulationDevice(groveLed: GroveLed) : GrovePulseWithModulationOutputDevice(groveLed)
}