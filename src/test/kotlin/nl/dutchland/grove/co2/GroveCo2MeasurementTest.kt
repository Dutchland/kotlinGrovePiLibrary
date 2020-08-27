package nl.dutchland.grove.co2

import nl.dutchland.grove.utility.TimeStamp
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class GroveCo2MeasurementTest {

    @Test
    fun valid() {
        val measurement : Co2Measurement = GroveCo2Measurement(ByteArray(9), TimeStamp.now())

    }
}