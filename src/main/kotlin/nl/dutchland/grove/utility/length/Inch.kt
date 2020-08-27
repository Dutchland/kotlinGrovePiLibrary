package nl.dutchland.grove.utility.length

object Inch : Length.Unit() {
    private const val INCH_TO_METER_FACTOR = 0.0252

    override val longName: String = "Inch"
    override val shortName: String = "inch"

    override fun fromMeter(valueInMeter: Double): Double {
        return valueInMeter / INCH_TO_METER_FACTOR
    }

    override fun toMeter(value: Double): Double {
        return value * INCH_TO_METER_FACTOR
    }
}