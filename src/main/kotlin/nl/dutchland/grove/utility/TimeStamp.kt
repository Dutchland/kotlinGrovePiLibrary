package nl.dutchland.grove.utility

import java.sql.Time
import java.time.LocalDateTime

data class TimeStamp internal constructor(val millisecondsSinceEpoch : Long) {
    companion object {
        fun fromMillisecondsSinceEpoch(value : Long) : TimeStamp {
            return TimeStamp(value)
        }

        fun now() : TimeStamp {
            return TimeStamp(System.currentTimeMillis())
        }
    }
}