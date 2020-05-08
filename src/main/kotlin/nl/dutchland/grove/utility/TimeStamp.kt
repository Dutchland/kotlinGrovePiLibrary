package nl.dutchland.grove.utility

data class TimeStamp internal constructor(val millisecondsSinceEpoch : Long) : Comparable<TimeStamp> {
    companion object {
        fun fromMillisecondsSinceEpoch(value : Long) : TimeStamp {
            return TimeStamp(value)
        }

        fun now() : TimeStamp {
            return TimeStamp(System.currentTimeMillis())
        }
    }

    override fun compareTo(other: TimeStamp): Int {
        return other.millisecondsSinceEpoch.compareTo(millisecondsSinceEpoch)
    }
}