package nl.dutchland.grove.demo

import nl.dutchland.grove.utility.Euro
import nl.dutchland.grove.utility.length.Inch
import nl.dutchland.grove.utility.length.Length
import nl.dutchland.grove.utility.length.Meter
import nl.dutchland.grove.utility.temperature.Celsius
import nl.dutchland.grove.utility.temperature.Fahrenheit
import nl.dutchland.grove.utility.temperature.Kelvin
import nl.dutchland.grove.utility.temperature.Temperature
import nl.dutchland.grove.utility.weight.*
import java.time.DayOfWeek.*
import java.time.LocalDate

/**
 * 'Slimme' developers zorgen dat collega's ongemerkt fouten kunnen maken zodat zij het kunnen fixen
 */
private fun persistCurrentTemperature(sensor: TemperatureSensor) {
    // Welke schaal?
    val roomTemperature: Double = sensor.currentTemperature()
    println("Temperature in Kelvin, Celsius, Fahrenheit or some other scale?????")

    TemperatureRepository().persist(
            TemperatureUtil.celsiusToFahrenheit(roomTemperature))
}

private fun persistCurrentTemperature1(sensor: TemperatureSensor) {
    val roomTemperatureInCelsius: Double = sensor.currentTemperatureInCelsius()

    // Death by Util class. Aantal 'domme' utility methodes schaalt met: n * (n-1) --> n^2
    val temperatureInKelvin: Double = TemperatureUtil.kelvinToFahrenheit(roomTemperatureInCelsius)
    TemperatureRepository()
            .persistTemperatureInKelvin(temperatureInKelvin)
}


// Wat is hier het probleem? Temperatuurschaal en waarde zijn niet los te zien maar zijn in de code niet hard aan elkaar gekoppeld

/**
 * Waarom moet deze methode dingen weten over temperatuur schalen?. Weg met die slecht weg te mocken Utility methodes!!!!
 * Repository weet wel of hij in Kelvin of Celsius wil opslaan
 */
private fun persistCurrentTemperature2(sensor: TemperatureSensor) {
    val roomTemperature: Temperature = sensor.currentRoomTemperature()
    TemperatureRepository()
            .persist(roomTemperature)
}

private fun temperatureObject() {
    // Mensen maken fouten, maak het moeilijk om fouten te maken
    val roomTemperature: Temperature = Temperature.of(22.0, Celsius)
    val temperatureInKelvin: Double = roomTemperature.valueIn(Kelvin)

    println("The current room temperature is $temperatureInKelvin $Kelvin")
    println("In $Fahrenheit this would be ${roomTemperature.valueIn(Fahrenheit)}")
}

class TemperatureSensor() {
    /**
     * Return in Kelvin
     */
    fun currentTemperature(): Double {
        return 4.0
    }

    fun currentTemperatureInCelsius(): Double { // Extreme naming
        return 4.0
    }

    fun currentTemperatureInKelvin(): Double { // Extreme naming
        return 4.0
    }

    fun currentRoomTemperature(): Temperature {
        return Temperature.of(4.0, Celsius)
    }
}

class TemperatureRepository {
    /**
     * Temperature in Kelvin
     */
    fun persist(temperature: Double) {
        // Persisting
    }

    /**
     * Overloading niet mogelijk!!!!
     */
    fun persistTemperature2(temperatureInKelvin: Double) {
        // Persisting
    }

    fun persistTemperatureInKelvin(temperature: Double) { // Extreme naming
        // Persisting
    }

    fun persistTemperatureInCelsius(temperature: Double) { // Extreme naming
        // Persisting
    }

    fun persist(temperature: Temperature) {
        // Persisting
    }
}

private fun length() {
    val beamLength: Length = Length.of(1.0, Meter)
    val otherBeamLength: Length = Length.of(10.0, Inch)

    val totalLengthJavaWay: Length = beamLength.plus(otherBeamLength)
    val totalLength: Length = beamLength + otherBeamLength

    println("The total length is ${totalLength.valueIn(Inch)} $Inch")
}

/**
 * Lazy
 */
private fun weight() {
    val potatoWeights = listOf(
            Weight.of(10.0, Kilogram),
            Weight.of(5.0, Stone),
            Weight.of(1500.0, Gram)
    )

    val totalWeight = potatoWeights.sum()

    val cost = calculatePriceOfPotatoes(totalWeight)
    println("Price of the potatoes is: $cost")
}

private fun calculatePriceOfPotatoes(weight: Weight): Euro {
    val isFreeDay = LocalDate.now().dayOfWeek == MONDAY
    if (isFreeDay) {
        return Euro.fromCents(0)
    }

    val pricePerKg: Euro = Euro.fromCents(101)
    return pricePerKg * weight.valueIn(Kilogram)
}

private fun whyNotAnEnum() {
    // Niet closed for modification
    // Niet uitbreidbaar door derden

    val someLength = Length.of(100.1, SomeImperialLengthUnit)
    println("In meters this would be: ${someLength.valueIn(Meter)}")
}

enum class LengthUnit(
        val toMeterFunction: (Double) -> Double,
        val fromMeterFunction: (Double) -> Double,
        val unitName: String) {

    METER({ it }, { it }, "Meter"),
    MILLIMETER({ it * 1000.0 }, { it / 1000.0 }, "Millimeter"),
    INCH({ it * 0.0252 }, { it / 0.0252 }, "Inch")
}

private object SomeImperialLengthUnit : Length.Unit() {

    override fun fromMillimeter(valueInMillimeter: Double): Double {
        return valueInMillimeter * 99.8
    }

    override fun toMillimeter(value: Double): Double {
        return value / 99.8
    }

    override val name = "Some Imperial Unit"

}

class TemperatureUtil {
    companion object {

        fun kelvinToFahrenheit(value: Double): Double {
            // Validate
            return 1.0
        }

        fun celsiusToFahrenheit(value: Double): Double {
            // Validate
            return 1.0
        }
    }

}
