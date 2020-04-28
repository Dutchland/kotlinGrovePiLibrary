package nl.dutchland.grove.utility.temperature

object Fahrenheit : Temperature.Scale {
    override val name = "Fahrenheit"
    override val absoluteZero = Celsius.absoluteZero * 1.8 + 32.0

    override
    fun fromKelvin(valueInKelvin: Double): Double {
        return (valueInKelvin + Celsius.absoluteZero) * 1.8 + 32.0
    }

    override
    fun toKelvin(value: Double): Double {
        return (value -32.0) / 1.8 - Celsius.absoluteZero
    }
}