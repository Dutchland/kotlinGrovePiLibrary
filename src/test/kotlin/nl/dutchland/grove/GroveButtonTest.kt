package nl.dutchland.grove

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import nl.dutchland.grove.button.ButtonStatusChangedListener
import nl.dutchland.grove.button.GroveButton
import org.iot.raspberry.grovepi.GroveDigitalIn
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import kotlin.test.Test
import kotlin.test.assertEquals

class GroveButtonTest {
    @Test
    fun testButtonStatusGet() {
        testButtonStatus(true)
        testButtonStatus(false)
    }

    private fun testButtonStatus(expectedIsPressed: Boolean) {
        // Arrange
        val mockedDigitalIn = mock(GroveDigitalIn::class.java)
        Mockito.`when`(mockedDigitalIn.get())
                .thenReturn(expectedIsPressed)
        val groveButton = GroveButton(mockedDigitalIn)

        // Act
        val actualIsPressed = groveButton.isPressed();

        // Assert
        assertEquals(expectedIsPressed, actualIsPressed)
    }

    @Test
    fun testOnChangedListener_StatusChanges() {
        // Arrange
        val mockedDigitalIn = mock(GroveDigitalIn::class.java)
        val groveButton = GroveButton(mockedDigitalIn)

        val fakeListener = mock<ButtonStatusChangedListener>()
        groveButton.addStatusChangedListener(fakeListener)

        // When
        Mockito.`when`(mockedDigitalIn.get()).thenReturn(true)
        Thread.sleep(101)
        Mockito.`when`(mockedDigitalIn.get()).thenReturn(false)
        Thread.sleep(101)

        // Then
        verify(fakeListener).invoke(true)
        verify(fakeListener).invoke(false)
    }

    @Test
    fun testOnChangedListener_StatusDoesNotChange() {
        // Arrange
        val mockedDigitalIn = mock(GroveDigitalIn::class.java)
        Mockito.`when`(mockedDigitalIn.get()).thenReturn(false)
        val groveButton = GroveButton(mockedDigitalIn)

        val fakeListener = mock<ButtonStatusChangedListener>()
        groveButton.addStatusChangedListener(fakeListener)

        // When
        Thread.sleep(101)

        // Then
        verify(fakeListener, times(0)).invoke(true)
        verify(fakeListener, times(0)).invoke(false)
    }
}