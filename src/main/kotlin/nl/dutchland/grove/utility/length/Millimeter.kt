package nl.dutchland.grove.utility.length

object Millimeter : Length.Unit() {
    override val name: String = "Millimeter"

    override fun fromMillimeter(valueInMillimeter: Double): Double {
        return valueInMillimeter
    }

    override fun toMillimeter(value: Double): Double {
        return value;
    }
}