package nl.dutchland.grove.utility.temperature

object Celcius : Temperature.Scale {
    override
    fun fromKelvin(valueInKelvin: Double): Double {
        return valueInKelvin + absoluteZero
    }

    override
    fun toKelvin(value: Double): Double {
        return value - absoluteZero
    }

    override
    fun validate(value: Double) {
        if (value < absoluteZero) {
            throw InvalidTemperatureException("Invalid temperature: $value. Minimal value is $absoluteZero Celcius")
        }
    }

    override
    val absoluteZero = -273.15
}