package nl.dutchland.grove.utility.derivedunits.electromagnetic.currentdensity

import nl.dutchland.grove.utility.baseunits.electriccurrent.ElectricCurrent
import nl.dutchland.grove.utility.derivedunits.mechanical.area.Area

class CurrentDensity private constructor(private val value: Double, private val unit: CurrentDensity.Unit) : Comparable<CurrentDensity> {
    private val currentDensityInAmperePerSquaredMeter by lazy {
        unit.toAmperePerSquaredMeter(value)
    }

    companion object {
        fun of(value: Double, unit: CurrentDensity.Unit): CurrentDensity {
            return CurrentDensity(value, unit)
        }
    }

    fun valueIn(unit: CurrentDensity.Unit): Double {
        if (this.unit == unit) {
            return this.value
        }
        return unit.fromAmperePerSquaredMeter(currentDensityInAmperePerSquaredMeter)
    }

    override operator fun compareTo(other: CurrentDensity): Int {
        if (unit == other.unit) {
            this.value.compareTo(other.value)
        }
        return this.currentDensityInAmperePerSquaredMeter.compareTo(other.currentDensityInAmperePerSquaredMeter)
    }

    operator fun plus(other: CurrentDensity): CurrentDensity {
        if (unit == other.unit) {
            return CurrentDensity(this.value + other.value, this.unit)
        }
        return CurrentDensity(other.currentDensityInAmperePerSquaredMeter + currentDensityInAmperePerSquaredMeter, AmperePerSquaredMeter)
    }

    operator fun minus(other: CurrentDensity): CurrentDensity {
        if (unit == other.unit) {
            return CurrentDensity(this.value - other.value, this.unit)
        }
        return CurrentDensity(this.currentDensityInAmperePerSquaredMeter - other.currentDensityInAmperePerSquaredMeter, AmperePerSquaredMeter)
    }

    operator fun times(factor: Double): CurrentDensity {
        return CurrentDensity(value * factor, unit)
    }

    interface Unit {
        fun fromAmperePerSquaredMeter(valueInAmpere: Double): Double
        fun toAmperePerSquaredMeter(value: Double): Double
        val shortName: String
        val longName: String

        override fun toString(): String

        companion object {
            fun ofFactorized(longName: String, shortName: String, toAmperePerSquaredMeterFactor: Double): Unit {
                return ParameterizedUnit(longName, shortName, toAmperePerSquaredMeterFactor)
            }

            fun ofParameterized(electricCurrentUnit : ElectricCurrent.Unit, areaUnit : Area.Unit): Unit {
                val factor = electricCurrentUnit.toAmpere(1.0) / areaUnit.toM2(1.0)
                val longName = "${electricCurrentUnit.longName} per ${areaUnit.longName}"
                val shortName = "${electricCurrentUnit.shortName} / ${areaUnit.shortName}"

                return ParameterizedUnit(longName, shortName, factor)
            }
        }
    }

    private class ParameterizedUnit(
            override val longName: String,
            override val shortName: String,
            private val toAmperePerSquaredMeterFactor: Double) : Unit {

        override fun fromAmperePerSquaredMeter(valueInAmpere: Double): Double = valueInAmpere / toAmperePerSquaredMeterFactor
        override fun toAmperePerSquaredMeter(value: Double): Double = value * toAmperePerSquaredMeterFactor

        override fun toString(): String {
            return longName
        }
    }
}
