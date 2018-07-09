package nl.dutchland.grove

import nl.dutchland.grove.button.GroveButton
import org.iot.raspberry.grovepi.GroveDigitalIn
import org.mockito.Mockito
import org.mockito.Mockito.mock
import kotlin.test.Test
import kotlin.test.assertEquals

class GroveButtonTest {
    @Test
    fun testButtonStatusGet() {
        testButtonStatus(true)
        testButtonStatus(false)
    }

    private fun testButtonStatus(isPressed: Boolean) {
        // Arrange
        val mockedDigitalIn = mock(GroveDigitalIn::class.java)
        Mockito.`when`(mockedDigitalIn.get())
                .thenReturn(isPressed)

        // Act
        val groveButton = GroveButton(mockedDigitalIn)

        // Assert
        assertEquals(isPressed, groveButton.isPressed())
    }

    @Test
    fun testOnChangedListener() {
        // Arrange
        val mockedDigitalIn = mock(GroveDigitalIn::class.java)
        Mockito.`when`(mockedDigitalIn.get()).thenReturn(true)
    }
}