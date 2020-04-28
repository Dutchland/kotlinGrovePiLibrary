package nl.dutchland.grove.utility

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import org.junit.jupiter.api.Test

class AssertTest {
    @Test
    fun testNotLargerThan_Larger() {
        // Arrange
        val mockAssertionFailedHandler: AssertionFailedHandler = mock()

        // Act
        2.0.assertNotLargerThan(1.0, mockAssertionFailedHandler)

        // Assert
        verify(mockAssertionFailedHandler).invoke()
    }

    @Test
    fun testNotLargerThan_Equal() {
        // Arrange
        val mockAssertionFailedHandler: AssertionFailedHandler = mock()

        // Act
        1.0.assertNotLargerThan(1.0, mockAssertionFailedHandler)

        // Assert
        verify(mockAssertionFailedHandler, times(0)).invoke()
    }

    @Test
    fun testNotLargerThan_Smaller() {
        // Arrange
        val mockAssertionFailedHandler: AssertionFailedHandler = mock()

        // Act
        1.0.assertNotLargerThan(2.0, mockAssertionFailedHandler)

        // Assert
        verify(mockAssertionFailedHandler, times(0)).invoke()
    }

    @Test
    fun testNotNegative_Positive() {
        // Arrange
        val mockAssertionFailedHandler: AssertionFailedHandler = mock()

        // Act
        1.0.assertNotNegative(mockAssertionFailedHandler)

        // Assert
        verify(mockAssertionFailedHandler, times(0)).invoke()
    }

    @Test
    fun testNotNegative_Zero() {
        // Arrange
        val mockAssertionFailedHandler: AssertionFailedHandler = mock()

        // Act
        0.0.assertNotNegative(mockAssertionFailedHandler)

        // Assert
        verify(mockAssertionFailedHandler, times(0)).invoke()
    }

    @Test
    fun testNotNegative_Negative() {
        // Arrange
        val mockAssertionFailedHandler: AssertionFailedHandler = mock()

        // Act
        (-1.0).assertNotNegative(mockAssertionFailedHandler)

        // Assert
        verify(mockAssertionFailedHandler).invoke()
    }
}