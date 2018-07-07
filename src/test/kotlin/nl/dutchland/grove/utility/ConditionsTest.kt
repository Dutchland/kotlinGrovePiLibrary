package nl.dutchland.grove.utility

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import kotlin.test.Test

class ConditionsTest {
    @Test
    fun testNotLargerThan_Larger() {
        // Arrange
        val mockErrorHandler: ErrorHandler = mock()

        // Act
        Conditions.assertNotLargerThan(2.0, 1.0, mockErrorHandler)

        // Assert
        verify(mockErrorHandler).invoke()
    }

    @Test
    fun testNotLargerThan_Equal() {
        // Arrange
        val mockErrorHandler: ErrorHandler = mock()

        // Act
        Conditions.assertNotLargerThan(1.0, 1.0, mockErrorHandler)

        // Assert
        verify(mockErrorHandler, times(0)).invoke()
    }

    @Test
    fun testNotLargerThan_Smaller() {
        // Arrange
        val mockErrorHandler: ErrorHandler = mock()

        // Act
        Conditions.assertNotLargerThan(1.0, 2.0, mockErrorHandler)

        // Assert
        verify(mockErrorHandler, times(0)).invoke()
    }

    @Test
    fun testNotNegative_Positive() {
        // Arrange
        val mockErrorHandler: ErrorHandler = mock()

        // Act
        Conditions.assertNotNegative(1.0, mockErrorHandler)

        // Assert
        verify(mockErrorHandler, times(0)).invoke()
    }

    @Test
    fun testNotNegative_Zero() {
        // Arrange
        val mockErrorHandler: ErrorHandler = mock()

        // Act
        Conditions.assertNotNegative(0.0, mockErrorHandler)

        // Assert
        verify(mockErrorHandler, times(0)).invoke()
    }

    @Test
    fun testNotNegative_Negative() {
        // Arrange
        val mockErrorHandler: ErrorHandler = mock()

        // Act
        Conditions.assertNotNegative(-1.0, mockErrorHandler)

        // Assert
        verify(mockErrorHandler).invoke()
    }
}