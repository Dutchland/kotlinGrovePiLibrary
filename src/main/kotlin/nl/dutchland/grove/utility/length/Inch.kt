package nl.dutchland.grove.utility.length

object Inch : Length.Unit() {
    private const val INCH_TO_MILLIMETER_FACTOR = 25.2

    override val name: String = "Inch"

    override fun fromMillimeter(valueInMillimeter: Double): Double {
        return valueInMillimeter / INCH_TO_MILLIMETER_FACTOR
    }

    override fun toMillimeter(value: Double): Double {
        return value * INCH_TO_MILLIMETER_FACTOR
    }
}