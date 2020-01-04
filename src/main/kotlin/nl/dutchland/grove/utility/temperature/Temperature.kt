package nl.dutchland.grove.utility.temperature

import nl.dutchland.grove.utility.Assert

data class Temperature private constructor(private val temperatureInKelvin: Double) {
    init {
        validate(temperatureInKelvin, Kelvin)
    }

    companion object {
        fun of(value: Double, scale: Scale): Temperature {
            validate(value, scale)
            return Temperature(scale.toKelvin(value))
        }

        private fun validate(value: Double, scale: Scale) {
            Assert.largerOrEquals(value, scale.absoluteZero)
            { throw InvalidTemperatureException("Invalid temperature: $value. Minimal value is ${scale.absoluteZero} ${scale.name}") }
        }

        val ABSOLUTE_ZERO: Temperature = Temperature(Kelvin.absoluteZero)
    }

    fun valueIn(scale: Scale): Double {
        return scale.fromKelvin(temperatureInKelvin)
    }

    operator fun compareTo(other: Temperature): Int {
        return this.temperatureInKelvin.compareTo(other.temperatureInKelvin)
    }

    interface Scale {
        fun fromKelvin(valueInKelvin: Double): Double
        fun toKelvin(value: Double): Double
        val absoluteZero: Double
        val name: String
    }
}

class InvalidTemperatureException(message: String) : IllegalArgumentException(message)