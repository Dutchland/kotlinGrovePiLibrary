package nl.dutchland.grove

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import org.iot.raspberry.grovepi.GroveDigitalIn
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
        val mockedDigitalIn = mock<GroveDigitalIn> {
            on { get() } doReturn isPressed
        }

        // Act
        val groveButton = GroveButton(mockedDigitalIn)

        // Assert
        assertEquals(isPressed, groveButton.isPressed())
    }
}