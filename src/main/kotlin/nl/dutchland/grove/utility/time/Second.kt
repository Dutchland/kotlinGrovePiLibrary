package nl.dutchland.grove.utility.time

object Second : Period.TimeUnit {
    override fun toSeconds(value: Double): Double {
        return value
    }

    override fun fromSeconds(value: Double): Double {
        return value
    }

    override val name = "Second"
}