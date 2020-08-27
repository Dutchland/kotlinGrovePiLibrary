package nl.dutchland.grove.utility.temperature

object Celsius : Temperature.Scale() {
    internal const val absoluteZero = -273.15

    override val name = "Celsius"

    override
    fun fromKelvin(valueInKelvin: Double): Double =
            valueInKelvin + absoluteZero

    override
    fun toKelvin(value: Double): Double =
            value - absoluteZero
}