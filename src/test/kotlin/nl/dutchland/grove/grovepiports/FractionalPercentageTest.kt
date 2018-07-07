package nl.dutchland.grove.grovepiports

import nl.dutchland.grove.utility.FractionalPercentage
import nl.dutchland.grove.utility.InvalidFractionException
import org.junit.Assert.*
import kotlin.test.Test

class FractionalPercentageTest {
    @Test
    fun testValidPercentage() {
        testValidPercentage(0.0, 0.0, 0.0)
        testValidPercentage(100.0, 100.0, 1.0)
        testValidPercentage(50.0, 50.0, 0.5)
    }

    private fun testValidPercentage(input: Double, expectedPercentage: Double, expectedFraction: Double) {
        val percentage = FractionalPercentage.ofPercentage(input)
        assertEquals(expectedPercentage, percentage.percentage, input / 1000.0)
        assertEquals(expectedFraction, percentage.fraction, input / 1000.0)
    }

    @Test
    fun testValidFraction() {
        testValidFraction(0.0, 0.0, 0.0)
        testValidFraction(0.5, 50.0, 0.5)
        testValidFraction(1.0, 100.0, 1.0)
    }

    private fun testValidFraction(input: Double, expectedPercentage: Double, expectedFraction: Double) {
        val percentage = FractionalPercentage.ofFraction(input)
        assertEquals(expectedPercentage, percentage.percentage, input / 1000.0)
        assertEquals(expectedFraction, percentage.fraction, input / 1000.0)
    }

    @Test
    fun testInvalidPercentage() {
        // Test negative percentage
        testInvalidPercentage(-1.0, "A percentage cannot be negative: " + -1.0)

        // Test bigger than 100
        testInvalidPercentage(150.0, "A fractional percentage cannot be larger than 100%: " + 150.0)
    }

    private fun testInvalidPercentage(input: Double, expectedErrorMessage: String) {
        ExceptionAssert.assertThrows { FractionalPercentage.ofPercentage(input) }
                .assertExactExceptionType(InvalidFractionException::class)
                .assertExceptionMessage(expectedErrorMessage)
    }

    @Test
    fun testInvalidFraction() {
        // Test negative fraction
        testInvalidFraction(-0.5, "A fraction cannot be negative: " + -0.5)

        // Test bigger than 1
        testInvalidFraction(1.1, "A fraction cannot be larger than 1.0: " + 1.1)
    }

    private fun testInvalidFraction(input: Double, expectedErrorMessage: String) {
        ExceptionAssert.assertThrows { FractionalPercentage.ofFraction(input) }
                .assertExactExceptionType(InvalidFractionException::class)
                .assertExceptionMessage(expectedErrorMessage)
    }
}