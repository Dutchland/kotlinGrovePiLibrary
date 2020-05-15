package nl.dutchland.grove.utility.weight

object Kilogram : Weight.Unit() {
    private const val KILOGRAM_TO_GRAM_FACTOR = 1_000

    override fun fromGrams(valueInGrams: Double): Double {
        return valueInGrams / KILOGRAM_TO_GRAM_FACTOR
    }

    override fun toGrams(value: Double): Double {
        return value * KILOGRAM_TO_GRAM_FACTOR
    }

    override val name = "Kilogram"
}