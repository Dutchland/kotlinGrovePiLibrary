package nl.dutchland.grove.utility.derivedunits.power

import nl.dutchland.grove.utility.baseunits.time.Second
import nl.dutchland.grove.utility.baseunits.time.Time
import nl.dutchland.grove.utility.derivedunits.energy.EnergyAmount

typealias PowerInWattProvider = () -> Double
typealias RadiantFlux = Power

data class Power internal constructor(private val powerInWattProvider: PowerInWattProvider) : Comparable<Power> {
    private val powerInWatt: Double by lazy {
        powerInWattProvider.invoke()
    }

    companion object {
        fun of(value: Double, unit: Unit): Power {
            return Power { unit.toWatt(value) }
        }
    }

    fun valueIn(unit: Unit): Double {
        return unit.fromWatt(powerInWatt)
    }

    override operator fun compareTo(other: Power): Int {
        return this.powerInWatt.compareTo(other.powerInWatt)
    }

    operator fun plus(other: Power): Power {
        return Power { this.powerInWatt + other.powerInWatt }
    }

    operator fun minus(other: Power): Power {
        return Power { this.powerInWatt - other.powerInWatt }
    }

    operator fun div(divider: Double): Power {
        return Power { this.powerInWatt / divider }
    }

    operator fun times(factor: Double): Power {
        return Power { this.powerInWatt * factor }
    }

    operator fun times(timePeriod: Time): EnergyAmount {
        return EnergyAmount { this.powerInWatt * timePeriod.valueIn(Second) }
    }

    interface Unit {
        val shortName: String
        val longName: String

        fun fromWatt(valueInWatt: Double): Double
        fun toWatt(value: Double): Double

        override fun toString(): String

        companion object {
            fun ofParameterized(energyUnit: EnergyAmount.Unit, timeUnit : Time.Unit): Unit {
                val factor = energyUnit.toJoule(1.0) / timeUnit.toSeconds(1.0)
                val shortName = "${energyUnit.shortName} / ${timeUnit.shortName}"
                return ParameterizedUnit(factor, shortName, "")
            }

            fun ofFactorized(toWattFactor: Double, shortName: String, longName: String): Unit {
                return ParameterizedUnit(toWattFactor, shortName, longName)
            }
        }
    }

    private class ParameterizedUnit(private val factor: Double,
                                    override val shortName: String,
                                    override val longName: String) : Unit {
        override fun fromWatt(valueInWatt: Double): Double = valueInWatt / factor
        override fun toWatt(value: Double): Double = value * factor

        override fun toString(): String = longName
    }
}