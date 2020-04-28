package nl.dutchland.grove.utility.temperature

object Celsius : Temperature.Scale {
    override val name = "Celsius"
    override val absoluteZero = -273.15

    override
    fun fromKelvin(valueInKelvin: Double): Double {
        return valueInKelvin + absoluteZero
    }

    override
    fun toKelvin(value: Double): Double {
        return value - absoluteZero
    }
}