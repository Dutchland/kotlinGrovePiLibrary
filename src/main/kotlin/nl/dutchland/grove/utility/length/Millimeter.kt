package nl.dutchland.grove.utility.length

object Millimeter : Length.Scale() {
    override val name: String = "Millimeter"

    override fun fromMillimeter(valueInMillimeter: Double): Double {
        return valueInMillimeter
    }

    override fun toMillimeter(value: Double): Double {
        return value;
    }
}