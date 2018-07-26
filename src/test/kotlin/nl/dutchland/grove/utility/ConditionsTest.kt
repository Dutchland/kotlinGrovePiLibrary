package nl.dutchland.grove.utility

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import kotlin.test.Test

class ConditionsTest {
    @Test
    fun testNotLargerThan_Larger() {
        // Arrange
        val mockAssertionFailedHandler: AssertionFailedHandler = mock()

        // Act
        Conditions.assertNotLargerThan(2.0, 1.0, mockAssertionFailedHandler)

        // Assert
        verify(mockAssertionFailedHandler).invoke()
    }

    @Test
    fun testNotLargerThan_Equal() {
        // Arrange
        val mockAssertionFailedHandler: AssertionFailedHandler = mock()

        // Act
        Conditions.assertNotLargerThan(1.0, 1.0, mockAssertionFailedHandler)

        // Assert
        verify(mockAssertionFailedHandler, times(0)).invoke()
    }

    @Test
    fun testNotLargerThan_Smaller() {
        // Arrange
        val mockAssertionFailedHandler: AssertionFailedHandler = mock()

        // Act
        Conditions.assertNotLargerThan(1.0, 2.0, mockAssertionFailedHandler)

        // Assert
        verify(mockAssertionFailedHandler, times(0)).invoke()
    }

    @Test
    fun testNotNegative_Positive() {
        // Arrange
        val mockAssertionFailedHandler: AssertionFailedHandler = mock()

        // Act
        Conditions.assertNotNegative(1.0, mockAssertionFailedHandler)

        // Assert
        verify(mockAssertionFailedHandler, times(0)).invoke()
    }

    @Test
    fun testNotNegative_Zero() {
        // Arrange
        val mockAssertionFailedHandler: AssertionFailedHandler = mock()

        // Act
        Conditions.assertNotNegative(0.0, mockAssertionFailedHandler)

        // Assert
        verify(mockAssertionFailedHandler, times(0)).invoke()
    }

    @Test
    fun testNotNegative_Negative() {
        // Arrange
        val mockAssertionFailedHandler: AssertionFailedHandler = mock()

        // Act
        Conditions.assertNotNegative(-1.0, mockAssertionFailedHandler)

        // Assert
        verify(mockAssertionFailedHandler).invoke()
    }
}