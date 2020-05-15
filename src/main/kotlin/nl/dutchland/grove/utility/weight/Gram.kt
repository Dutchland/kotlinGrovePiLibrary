package nl.dutchland.grove.utility.weight

object Gram : Weight.Unit() {
    override fun fromGrams(valueInGrams: Double): Double {
        return valueInGrams
    }

    override fun toGrams(value: Double): Double {
        return value
    }

    override val name = "Gram"
}