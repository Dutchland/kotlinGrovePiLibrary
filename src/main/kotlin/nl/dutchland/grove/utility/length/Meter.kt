package nl.dutchland.grove.utility.length

private const val METER_TO_MILLIMETER = 1_000

object Meter : Length.Unit() {
    override val name: String = "Meter"

    override fun fromMillimeter(valueInMillimeter: Double): Double {
        return valueInMillimeter / METER_TO_MILLIMETER
    }

    override fun toMillimeter(value: Double): Double {
        return value * METER_TO_MILLIMETER
    }
}