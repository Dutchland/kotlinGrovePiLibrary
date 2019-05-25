package nl.dutchland.grove.utility.temperature

object Kelvin : Temperature.Scale {
    override val name = "Kelvin"

    override fun fromKelvin(valueInKelvin: Double): Double {
        return valueInKelvin
    }

    override fun toKelvin(value: Double): Double {
        return value
    }

    override val absoluteZero = 0.0
}
