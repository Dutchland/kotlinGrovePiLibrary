package nl.dutchland.grove.utility.derivedunits.speed

import nl.dutchland.grove.utility.baseunits.length.Length
import nl.dutchland.grove.utility.baseunits.length.Meter
import nl.dutchland.grove.utility.baseunits.length.Millimeter
import nl.dutchland.grove.utility.baseunits.time.Minute
import nl.dutchland.grove.utility.baseunits.time.Period
import nl.dutchland.grove.utility.baseunits.time.Second
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource


internal class SpeedUnitTest {

    @Test
    fun bla() {
        assertEquals(10.0, KilometerPerHour.toMeterPerSecond(36.0))
        assertEquals(1.0, Speed.Unit(Millimeter, Second).toMeterPerSecond(1000.0))
        assertEquals(1.0, (Millimeter / Second).toMeterPerSecond(1000.0))
    }


//    companion object {
//        @JvmStatic
//        private fun speedConversionTests(): List<Arguments?>? {
//            return listOf(
//                    Arguments.of(Speed.of(1.0, Meter).per(Second), 1000.0, Meter, Second),
//                    Arguments.of(Speed.of(1.0, Meter).per(Second), 1000.0, Meter, Minute),
//                    Arguments.of(Speed.of(1.0, Meter).per(Minute), 1000.0, Millimeter, Second),
//                    Arguments.of(Speed.of(1.0, Meter).per(Minute), 1000.0, Meter, Minute),
//                    Arguments.of(Speed.of(1.0, Meter).per(Second), 1000.0, Meter, Second),
//                    Arguments.of(Speed.of(1.0, Meter).per(Second), 1000.0, Meter, Second),
//                    Arguments.of(1.0, Meter, Second, 60.0, Meter, Minute),
//                    Arguments.of(1000.0, Millimeter, Second, 1.0, Meter, Second),
//                    Arguments.of(1000.0, Millimeter, Minute, 1.0, Meter, Second)
//            )
//        }
//    }
//
//    @ParameterizedTest
//    @MethodSource("speedConversionTests")
//    fun bla(speed: Double, lengthUnit: Length.Unit, timeUnit: Period.TimeUnit, speedConversion: Double, lengthUnitConversion: Length.Unit, timeUnitConversion: Period.TimeUnit) {
//        val originalSpeed = Speed.of(speed, lengthUnit).per(timeUnit)
//        assertEquals(speedConversion, originalSpeed.valueIn(lengthUnitConversion, timeUnitConversion))
//    }
}
