package nl.dutchland.grove.utility

class Kelvin : Temperature.Scale {
    override fun fromKelvin(valueInKelvin: Double): Double {
        return valueInKelvin
    }

    override fun toKelvin(value: Double): Double {
        return value
    }

    override fun validate(value: Double) {
        if (value < absoluteZero) {
            throw InvalidTemperatureException("Invalid temperature: $value. Minimal value is $absoluteZero Kelvin")
        }    }

    override val absoluteZero = 0.0
}
