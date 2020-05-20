package nl.dutchland.grove.utility.temperature

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class TemperaturePerformanceTest {
    @Test
    fun test() {
        val timestamp = System.currentTimeMillis()

        0.rangeTo(1_000_000)
                .forEach {
                    val temperature = Temperature.of(it.toDouble(), Celsius)
                    val temperatureInFahrenheit = temperature.valueIn(Fahrenheit)
                    val bla = 1.0 + temperatureInFahrenheit
                }

        println("Duration: ${System.currentTimeMillis() - timestamp}")
    }

    @Test
    fun test2() {
        val timestamp = System.currentTimeMillis()

        0.rangeTo(1_000_000)
                .forEach {
                    val temperatureInFahrenheit = (it + Celsius.absoluteZero) * 1.8 + 32.0
                    val bla = 1.0 + temperatureInFahrenheit
                }

        println("Duration: ${System.currentTimeMillis() - timestamp}")
    }
}