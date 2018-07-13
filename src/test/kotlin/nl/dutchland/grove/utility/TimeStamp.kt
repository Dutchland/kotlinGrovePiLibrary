package nl.dutchland.grove.utility

data class TimeStamp internal constructor(val millisecondsSinceEpoch : Long) {
    companion object {
        fun fromMillisecondsSinceEpoch(value : Long) : TimeStamp {
            return TimeStamp(value)
        }
    }
}