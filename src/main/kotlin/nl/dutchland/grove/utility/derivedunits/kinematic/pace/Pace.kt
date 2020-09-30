package nl.dutchland.grove.utility.derivedunits.kinematic.pace

import nl.dutchland.grove.utility.baseunits.length.Length
import nl.dutchland.grove.utility.baseunits.length.Meter
import nl.dutchland.grove.utility.baseunits.length.m
import nl.dutchland.grove.utility.baseunits.time.Period
import nl.dutchland.grove.utility.baseunits.time.Second
import nl.dutchland.grove.utility.baseunits.time.Time
import nl.dutchland.grove.utility.baseunits.time.s
import nl.dutchland.grove.utility.derivedunits.kinematic.acceleration.Acceleration
import nl.dutchland.grove.utility.derivedunits.kinematic.volumetricflow.VolumetricFlow
import nl.dutchland.grove.utility.derivedunits.mechanical.area.Area

typealias Velocity = Pace

private typealias PaceInSecondPerMeterProvider = () -> Double

data class Pace internal constructor(private val speedInSecondPerMeterProvider: PaceInSecondPerMeterProvider) : Comparable<Pace> {
    private val paceInSecondPerMeter: Double by lazy {
        speedInSecondPerMeterProvider.invoke()
    }

    companion object {
        fun of(value: Double, unit: Pace.Unit): Pace {
            return Pace { unit.toSecondPerMeter(value) }
        }
    }

    fun valueIn(unit: Pace.Unit): Double {
        return unit.fromSecondPerMeter(this.paceInSecondPerMeter)
    }

    operator fun plus(other: Pace): Pace {
        return Pace { this.paceInSecondPerMeter + other.paceInSecondPerMeter }
    }

    operator fun minus(other: Pace): Pace {
        return Pace { this.paceInSecondPerMeter - other.paceInSecondPerMeter }
    }

    operator fun times(length: Length): Time {
        return Time.of(this.paceInSecondPerMeter * length.valueIn(Meter), Second)
    }

    operator fun times(factor: Double): Pace {
        return Pace { this.paceInSecondPerMeter * factor }
    }

    operator fun div(timeUnit: Time.Unit): Acceleration {
        return Acceleration.of(this.paceInSecondPerMeter, (m/s)/timeUnit)
    }

    override operator fun compareTo(other: Pace): Int {
        return this.paceInSecondPerMeter.compareTo(other.paceInSecondPerMeter)
    }

    interface Unit {
        val shortName: String
        val longName: String

        fun fromSecondPerMeter(valueInMeterPerSecond: Double): Double
        fun toSecondPerMeter(value: Double): Double

        override fun toString(): String
    }

    private class ParameterizedUnit(private val timeUnit: Time.Unit, private val lengthUnit: Length.Unit) : Unit {
        override fun fromSecondPerMeter(valueInMeterPerSecond: Double): Double {
            return lengthUnit.fromMeter(valueInMeterPerSecond) / timeUnit.fromSeconds(1.0)
        }

        override fun toSecondPerMeter(value: Double): Double {
            return lengthUnit.toMeter(value) / timeUnit.toSeconds(1.0)
        }

        override val shortName: String = "${timeUnit.shortName}/${lengthUnit.shortName}"
        override val longName: String = "${timeUnit.longName} per ${lengthUnit.longName}"

        override fun toString(): String {
            return longName
        }
    }
}