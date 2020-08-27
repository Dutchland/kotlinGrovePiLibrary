package nl.dutchland.grove.utility.mass

object Kilogram : Mass.Unit() {
    override val longName = "Kilogram"
    override val shortName = "kg"

    override fun fromKilograms(valueInKilogram: Double): Double =valueInKilogram
    override fun toKiloGrams(value: Double): Double =value
}