package nl.dutchland.grove.utility.weight

import nl.dutchland.grove.utility.weight.Weight.Scale

private const val STONE_TO_GRAMS = 635_000

object Stone : Scale() {
    override fun fromGrams(valueInGrams: Double): Double {
        return valueInGrams / STONE_TO_GRAMS
    }

    override fun toGrams(value: Double): Double {
        return value * STONE_TO_GRAMS
    }

    override val name = "Stone"
}