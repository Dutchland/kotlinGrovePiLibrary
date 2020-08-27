package nl.dutchland.grove.utility.length

object Meter : Length.Unit() {
    override val longName: String = "Meter"
    override val shortName: String = "m"

    override fun fromMeter(valueInMeter: Double): Double = valueInMeter
    override fun toMeter(value: Double): Double = value
}