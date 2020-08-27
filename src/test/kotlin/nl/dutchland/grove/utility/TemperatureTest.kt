package nl.dutchland.grove.utility

import com.nhaarman.mockito_kotlin.mock
import nl.dutchland.grove.utility.temperature.InvalidTemperatureException
import nl.dutchland.grove.utility.temperature.Kelvin
import nl.dutchland.grove.utility.temperature.Temperature
import org.junit.jupiter.api.Assertions.*
import org.mockito.Mockito
import testutility.ExceptionAssert
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*
import testutility.ExceptionAssert.Companion.assertNotThrows
import testutility.ExceptionAssert.Companion.assertThrows

class TemperatureTest {

    @Test
    fun testInvalidTemperature() {
        val mockedTemperatureScale = mock<Temperature.Scale>()
        `when`(mockedTemperatureScale.name)
                .thenReturn("MockedTemperatureScale")
        `when`(mockedTemperatureScale.toKelvin(ArgumentMatchers.any()))
                .thenReturn(-1.0)

        assertThrows { Temperature.of(-1.0, mockedTemperatureScale) }
                .assertExactExceptionType(InvalidTemperatureException::class)
                .assertExceptionMessage("Invalid temperature: -1.0. Minimal value is -100.0 MockedTemperatureScale")
    }

    @Test
    fun testInvalidTemperature_copy() {
        val mockedTemperatureScale = mock<Temperature.Scale>()
        `when`(mockedTemperatureScale.name)
                .thenReturn("MockedTemperatureScale")

        val validTemperature = Temperature.of(0.0, mockedTemperatureScale)

        assertThrows { validTemperature.copy(-1.0) }
                .assertExactExceptionType(InvalidTemperatureException::class)
                .assertExceptionMessage("Invalid temperature: -1.0. Minimal value is 0.0 ${Kelvin.name}")
    }

    @Test
    fun testOfTemperature_ValidationSucces() {
        val mockedTemperatureScale = mock<Temperature.Scale>()
        assertNotThrows { Temperature.of(1.0, mockedTemperatureScale) }
    }

    @Test
    fun testOfTemperature() {
        // Arrange
        val mockedTemperatureScale = mock<Temperature.Scale>()
        `when`(mockedTemperatureScale.toKelvin(1.0))
                .thenReturn(2.0)
        `when`(mockedTemperatureScale.fromKelvin(2.0))
                .thenReturn(4.0)

        val temperature = Temperature.of(1.0, mockedTemperatureScale)

        // Act
        val value = temperature.valueIn(mockedTemperatureScale)

        // Assert
        assertEquals(4.0, value, 0.01)
    }
}