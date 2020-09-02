package nl.dutchland.grove.utility.baseunits.time

typealias s = Second
object Second : Period.TimeUnit() {
    override val longName = "Second"
    override val shortName = "s"

    override fun toSeconds(value: Double): Double = value
    override fun fromSeconds(valueInSeconds: Double): Double = valueInSeconds
}