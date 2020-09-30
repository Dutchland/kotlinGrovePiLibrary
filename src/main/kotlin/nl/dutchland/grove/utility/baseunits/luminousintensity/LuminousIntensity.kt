package nl.dutchland.grove.utility.baseunits.luminousintensity

import nl.dutchland.grove.utility.derivedunits.mechanical.area.Area
import nl.dutchland.grove.utility.derivedunits.photometric.luminance.Luminance

class LuminousIntensity private constructor(private val value: Double, private val unit: LuminousIntensity.Unit) : Comparable<LuminousIntensity> {
    private val intensityInCandela: Double by lazy {
        unit.toCandela(value)
    }

    companion object {
        fun of(value: Double, unit: LuminousIntensity.Unit): LuminousIntensity {
            return LuminousIntensity(value, unit)
        }
    }

    fun valueIn(unit: LuminousIntensity.Unit): Double {
        if (this.unit == unit) {
            return this.value
        }
        return unit.fromCandela(intensityInCandela)
    }

    override operator fun compareTo(other: LuminousIntensity): Int {
        if (unit == other.unit) {
            this.value.compareTo(other.value)
        }
        return this.intensityInCandela.compareTo(other.intensityInCandela)
    }

    interface Unit {
        val shortName: String
        val longName: String

        fun fromCandela(valueInCandela: Double): Double
        fun toCandela(value: Double): Double

        override fun toString(): String

        operator fun div(areaUnit: Area.Unit): Luminance.Unit {
            return Luminance.ParameterizedUnit(this, areaUnit)
        }
    }

    internal class ParameterizedUnit(
            override val longName: String,
            override val shortName: String,
            private val toCandelaFactor: Double) : LuminousIntensity.Unit {

        override fun fromCandela(valueInCandela: Double): Double = valueInCandela / toCandelaFactor
        override fun toCandela(value: Double): Double = value * toCandelaFactor

        override fun toString(): String {
            return longName
        }
    }
}


