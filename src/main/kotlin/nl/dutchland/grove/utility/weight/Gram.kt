package nl.dutchland.grove.utility.weight

import nl.dutchland.grove.utility.weight.Weight.Scale

object Gram : Scale() {
    override fun fromGrams(valueInGrams: Double): Double {
        return valueInGrams
    }

    override fun toGrams(value: Double): Double {
        return value
    }

    override val name = "Gram"
}