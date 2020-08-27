package nl.dutchland.grove.utility.time

object Second : Period.TimeUnit() {
    override val longName = "Second"
    override val shortName = "s"

    override fun toSeconds(value: Double): Double = value
    override fun fromSeconds(valueInSeconds: Double): Double = valueInSeconds
}