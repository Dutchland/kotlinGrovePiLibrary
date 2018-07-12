package nl.dutchland.grove.utility

import com.nhaarman.mockito_kotlin.mock
import org.junit.Assert
import org.mockito.Mockito
import testutility.ExceptionAssert
import kotlin.test.Test

class TemperatureTest {
    @Test
    fun testOfTemperature_ValidationFails() {
        val mockedTemperatureScale = mock<Temperature.Scale>()
        Mockito.`when`(mockedTemperatureScale.validate(1.0))
                .thenThrow(InvalidTemperatureException::class.java)

        ExceptionAssert.assertThrows { Temperature.of(1.0, mockedTemperatureScale) }
                .assertExactExceptionType(InvalidTemperatureException::class)
    }

    @Test
    fun testOfTemperature_ValidationSucces() {
        val mockedTemperatureScale = mock<Temperature.Scale>()
        ExceptionAssert.assertNotThrows { Temperature.of(1.0, mockedTemperatureScale) }
    }

    @Test
    fun testOfTemperature() {
        // Arrange
        val mockedTemperatureScale = mock<Temperature.Scale>()
        Mockito.`when`(mockedTemperatureScale.toKelvin(1.0))
                .thenReturn(2.0)
        Mockito.`when`(mockedTemperatureScale.fromKelvin(2.0))
                .thenReturn(4.0)

        val temperature = Temperature.of(1.0, mockedTemperatureScale)

        // Act
        val value = temperature.valueIn(mockedTemperatureScale)

        // Assert
        Assert.assertEquals(4.0, value, 0.01)
    }
}