package nl.dutchland.grove.utility.time

object Second : Duration.TimeScale {
    override fun toSeconds(value: Double): Double {
        return value
    }

    override fun fromSeconds(value: Double): Double {
        return value
    }
}