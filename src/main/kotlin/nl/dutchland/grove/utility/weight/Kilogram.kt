package nl.dutchland.grove.utility.weight

import nl.dutchland.grove.utility.weight.Weight.Scale

object Kilogram : Scale() {
    private const val KILOGRAM_TO_GRAM_FACTOR = 1_000

    override fun fromGrams(valueInGrams: Double): Double {
        return valueInGrams / KILOGRAM_TO_GRAM_FACTOR
    }

    override fun toGrams(value: Double): Double {
        return value * KILOGRAM_TO_GRAM_FACTOR
    }

    override val name = "Kilogram"
}