package nl.dutchland.grove.utility.length

object Millimeter : Length.Unit() {
    private const val MILLI_METER_TO_METER = 0.001

    override val shortName: String = "mm"
    override val longName: String = "Millimeter"

    override fun fromMeter(valueInMeter: Double): Double =
            valueInMeter / MILLI_METER_TO_METER

    override fun toMeter(value: Double): Double =
            value * MILLI_METER_TO_METER
}