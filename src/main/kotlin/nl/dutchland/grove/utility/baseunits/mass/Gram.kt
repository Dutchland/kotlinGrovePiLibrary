package nl.dutchland.grove.utility.baseunits.mass

typealias gr = Gram

object Gram : Mass.Unit() {
    private const val GRAM_TO_KILOGRAM_FACTOR = 0.001

    override val longName = "Gram"
    override val shortName = "gr"

    override fun fromKilograms(valueInKilogram: Double): Double =
            valueInKilogram / GRAM_TO_KILOGRAM_FACTOR

    override fun toKiloGrams(value: Double): Double =
            value * GRAM_TO_KILOGRAM_FACTOR
}