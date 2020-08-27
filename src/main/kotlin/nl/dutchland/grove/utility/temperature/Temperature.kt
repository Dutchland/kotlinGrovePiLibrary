package nl.dutchland.grove.utility.temperature

import nl.dutchland.grove.utility.assertLargerOrEquals

data class Temperature internal constructor(private val temperatureInKelvin: Double) : Comparable<Temperature> {
    companion object {
        fun of(value: Double, scale: Scale): Temperature {
            validate(value, scale)
            return Temperature(scale.toKelvin(value))
        }

        private fun validate(value: Double, scale: Scale) {
            val absoluteTemperatureInScale = scale.fromKelvin(Kelvin.absoluteZero)

            value.assertLargerOrEquals(absoluteTemperatureInScale)
            { throw InvalidTemperatureException("Invalid temperature: $value. Minimal value is $absoluteTemperatureInScale ${scale.name}") }
        }

        val ABSOLUTE_ZERO: Temperature = Temperature(Kelvin.absoluteZero)
    }

    fun valueIn(scale: Scale): Double {
        return scale.fromKelvin(temperatureInKelvin)
    }

    override operator fun compareTo(other: Temperature): Int {
        return this.temperatureInKelvin.compareTo(other.temperatureInKelvin)
    }

    abstract class Scale {
        abstract fun fromKelvin(valueInKelvin: Double): Double
        abstract fun toKelvin(value: Double): Double
        abstract val name: String

        override fun toString(): String {
            return name
        }
    }
}