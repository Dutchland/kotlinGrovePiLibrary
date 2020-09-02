package nl.dutchland.grove.utility.baseunits.temperature

object Kelvin : Temperature.Scale() {
    internal const val absoluteZero = 0.0
    override val name = "Kelvin"

    override fun fromKelvin(valueInKelvin: Double): Double = valueInKelvin
    override fun toKelvin(value: Double): Double = value
}
