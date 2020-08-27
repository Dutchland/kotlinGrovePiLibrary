package nl.dutchland.grove.utility.density

import nl.dutchland.grove.utility.length.Length
import nl.dutchland.grove.utility.mass.Mass
import nl.dutchland.grove.utility.volume.Volume

typealias MassInKiloGramPerCubicMeter = () -> Double

data class MassDensity internal constructor(private val massInKiloGramPerCubicMeterProvider: MassInKiloGramPerCubicMeter) {
    private val massInKiloGramPerCubicMeter: Double by lazy {
        massInKiloGramPerCubicMeterProvider.invoke()
    }

    companion object {
        fun of(value: Double, unit: Mass.Unit): Builder {
            return Builder(value, unit)
        }
    }

    class Builder internal constructor(private val value: Double, private val unit: Mass.Unit) {
        fun perCubic(lengthUnit: Length.Unit): MassDensity {
            return MassDensity { unit.toKiloGrams(value) / lengthUnit.toMeter(1.0) }
        }

        fun per(volumeUnit: Volume.Unit): MassDensity {
            return MassDensity { unit.toKiloGrams(value) / volumeUnit.toM2(1.0) }
        }
    }

    fun valueIn(unit: Unit): Double {
        return unit.fromJoule(massInKiloGramPerCubicMeter)
    }

//    override operator fun compareTo(other: EnergyAmount): Int {
//        return this.energyInJoule.compareTo(other.energyInJoule)
//    }

//    operator fun plus(other: Area): Area {
//        return Area { this.areaInM2 + other.areaInM2 }
//    }
//
//    operator fun minus(other: Area): Area {
//        return Area { this.areaInM2 - other.areaInM2 }
//    }
//
//    operator fun div(divider: Double): Area {
//        return Area { this.areaInM2 / divider }
//    }
//
//    operator fun times(factor: Double): Area {
//        return Area { this.areaInM2 * factor }
//    }

    abstract class Unit {
        abstract fun fromJoule(valueInJoule: Double): Double
        abstract fun toJoule(value: Double): Double

        abstract val shortName: String
        abstract val longName: String

        override fun toString(): String {
            return longName
        }
    }
}