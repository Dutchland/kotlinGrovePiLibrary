package nl.dutchland.grove.utility.temperature

object Celcius : Temperature.Scale {
    override val name = "Celcius"

    override
    fun fromKelvin(valueInKelvin: Double): Double {
        return valueInKelvin + absoluteZero
    }

    override
    fun toKelvin(value: Double): Double {
        return value - absoluteZero
    }

    override
    val absoluteZero = -273.15
}