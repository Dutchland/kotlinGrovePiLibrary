package nl.dutchland.grove.button

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import org.iot.raspberry.grovepi.GroveDigitalIn
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

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
        val groveButton = GroveButton(mockedDigitalIn, emptyList())

        // Act
        val actualIsPressed = groveButton.status

        // Assert
//        assertEquals(expectedIsPressed, actualIsPressed)
    }

    @Test
    fun testOnChangedListener_StatusChanges() {
        // Arrange
        val mockedDigitalIn = mock(GroveDigitalIn::class.java)
        Mockito.`when`(mockedDigitalIn.get()).thenReturn(true)
//        val groveButton = GroveButton(mockedDigitalIn)

        val fakeListener = mock<ButtonStatusChangedListener>()
//        groveButton.addStatusChangedListener(fakeListener)

        // Act
        Mockito.`when`(mockedDigitalIn.get()).thenReturn(false)
        Thread.sleep(101)

//        // Assert
//        verify(fakeListener).invoke(true)
//        verify(fakeListener).invoke(false)
    }

    @Test
    fun testOnChangedListener_StatusDoesNotChange() {
//        // Arrange
//        val mockedDigitalIn = mock(GroveDigitalIn::class.java)
//        Mockito.`when`(mockedDigitalIn.get()).thenReturn(false)
//        val groveButton = GroveButton(mockedDigitalIn)
//
//        val fakeListener = mock<ButtonStatusChangedListener>()
//        groveButton.addStatusChangedListener(fakeListener)
//
//        // Act
//        Thread.sleep(101)
//
//        // Assert
//        verify(fakeListener, times(0)).invoke(true)
//        verify(fakeListener).invoke(false)
    }
}