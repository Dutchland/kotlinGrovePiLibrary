package nl.dutchland.grove.utility.derivedunits.magneticfieldstrength

import nl.dutchland.grove.utility.baseunits.electriccurrent.ElectricCurrent
import nl.dutchland.grove.utility.baseunits.length.Length

class MagneticFieldStrength private constructor(private val value: Double, private val unit: MagneticFieldStrength.Unit) : Comparable<MagneticFieldStrength> {
    private val currentDensityInAmperePerSquaredMeter by lazy {
        unit.toAmperePerMeter(value)
    }

    companion object {
        fun of(value: Double, unit: MagneticFieldStrength.Unit): MagneticFieldStrength {
            return MagneticFieldStrength(value, unit)
        }
    }

    fun valueIn(unit: MagneticFieldStrength.Unit): Double {
        if (this.unit == unit) {
            return this.value
        }
        return unit.fromAmperePerMeter(currentDensityInAmperePerSquaredMeter)
    }

    override operator fun compareTo(other: MagneticFieldStrength): Int {
        if (unit == other.unit) {
            this.value.compareTo(other.value)
        }
        return this.currentDensityInAmperePerSquaredMeter.compareTo(other.currentDensityInAmperePerSquaredMeter)
    }

    operator fun plus(other: MagneticFieldStrength): MagneticFieldStrength {
        if (unit == other.unit) {
            return MagneticFieldStrength(this.value + other.value, this.unit)
        }
        return MagneticFieldStrength(other.currentDensityInAmperePerSquaredMeter + currentDensityInAmperePerSquaredMeter, AmperePerMeter)
    }

    operator fun minus(other: MagneticFieldStrength): MagneticFieldStrength {
        if (unit == other.unit) {
            return MagneticFieldStrength(this.value - other.value, this.unit)
        }
        return MagneticFieldStrength(this.currentDensityInAmperePerSquaredMeter - other.currentDensityInAmperePerSquaredMeter, AmperePerMeter)
    }

    operator fun times(factor: Double): MagneticFieldStrength {
        return MagneticFieldStrength(value * factor, unit)
    }

    interface Unit {
        fun fromAmperePerMeter(valueInAmpere: Double): Double
        fun toAmperePerMeter(value: Double): Double
        val shortName: String
        val longName: String

        override fun toString(): String

        companion object {
            fun ofFactorized(longName: String, shortName: String, toAmperePerMeterFactor: Double): Unit {
                return ParameterizedUnit(longName, shortName, toAmperePerMeterFactor)
            }

            fun ofParameterized(electricCurrentUnit : ElectricCurrent.Unit, lengthUnit : Length.Unit): Unit {
                val factor = electricCurrentUnit.toAmpere(1.0) / lengthUnit.toMeter(1.0)
                val longName = "${electricCurrentUnit.longName} per ${lengthUnit.longName}"
                val shortName = "${electricCurrentUnit.shortName} / ${lengthUnit.shortName}"

                return ParameterizedUnit(longName, shortName, factor)
            }
        }
    }

    private class ParameterizedUnit(
            override val longName: String,
            override val shortName: String,
            private val toAmperePerMeterFactor: Double) : Unit {

        override fun fromAmperePerMeter(valueInAmpere: Double): Double = valueInAmpere / toAmperePerMeterFactor
        override fun toAmperePerMeter(value: Double): Double = value * toAmperePerMeterFactor

        override fun toString(): String {
            return longName
        }
    }
}
