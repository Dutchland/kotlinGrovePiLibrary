package nl.dutchland.grove.utility.length

object Kilometer : Length.Unit() {
    private const val KILO_METER_TO_METER = 1_000

    override val longName: String = "Kilometer"
    override val shortName: String = "km"

    override fun fromMeter(valueInMeter: Double): Double =
            valueInMeter / KILO_METER_TO_METER

    override fun toMeter(value: Double): Double =
            value * KILO_METER_TO_METER
}