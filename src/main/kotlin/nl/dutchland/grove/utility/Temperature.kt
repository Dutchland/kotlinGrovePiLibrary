package nl.dutchland.grove.utility

class Temperature private constructor(private val temperatureInKelvin: Double) {
    companion object {
        fun of(value: Double, scale : Scale): Temperature {
            scale.validate(value)
            return Temperature(scale.toKelvin(value))
        }

        val ABSOLUTE_ZERO: Temperature = Temperature(Kelvin().absoluteZero)
    }

    fun valueIn(scale : Scale): Double {
        return scale.fromKelvin(temperatureInKelvin);
    }

    interface Scale {
        fun fromKelvin(valueInKelvin : Double) : Double
        fun toKelvin(value : Double) : Double
        fun validate(value: Double)
        val absoluteZero : Double
    }
}

class InvalidTemperatureException(message: String) : RuntimeException(message)