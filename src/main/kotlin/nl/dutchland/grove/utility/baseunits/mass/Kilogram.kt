package nl.dutchland.grove.utility.baseunits.mass

typealias kg = Kilogram
object Kilogram : Mass.Unit() {
    override val longName = "Kilogram"
    override val shortName = "kg"

    override fun fromKilograms(valueInKilogram: Double): Double = valueInKilogram
    override fun toKiloGrams(value: Double): Double = value
}