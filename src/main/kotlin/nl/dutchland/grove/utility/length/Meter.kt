package nl.dutchland.grove.utility.length

object Meter : Length.Scale() {
    override val name: String = "Meter"

    override fun fromMillimeter(valueInMillimeter: Double): Double {
        return valueInMillimeter / 1_000
    }

    override fun toMillimeter(value: Double): Double {
        return value * 1_000
    }
}