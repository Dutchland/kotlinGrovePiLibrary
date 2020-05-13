package nl.dutchland.grove.demo

import nl.dutchland.grove.button.ButtonStatus
import nl.dutchland.grove.events.MuteEvent
import nl.dutchland.grove.utility.length.Inch
import nl.dutchland.grove.utility.length.Length
import nl.dutchland.grove.utility.length.Meter
import nl.dutchland.grove.utility.temperature.Celsius
import nl.dutchland.grove.utility.temperature.Fahrenheit
import nl.dutchland.grove.utility.temperature.Kelvin
import nl.dutchland.grove.utility.temperature.Temperature

/**
 * 'Goede' developers zorgen dat collega's makkelijk fouten kunnen maken zodat zij het kunnen fixen.
 */
private fun persistCurrentTemperature(sensor: TemperatureSensor) {
    // Temperature in what scale?
    val roomTemperature: Double = sensor.currentTemperature()
    println("Temperature in Kelvin, Celsius, Fahrenheit or some other scale?????")

    TemperatureRepository().persist(
            TemperatureUtil.celsiusToFahrenheit(roomTemperature))
}

/**
 * Waarom moet ik dingen weten over temperatuur schalen?
 */
private fun persistCurrentTemperature1(sensor: TemperatureSensor) {
    val roomTemperatureInCelsius: Double = sensor.currentTemperatureInCelsius()

    // Death by Util class syndrome
    val temperatureInKelvin = TemperatureUtil.kelvinToFahrenheit(roomTemperatureInCelsius)
    TemperatureRepository()
            .persistTemperatureInKelvin(temperatureInKelvin)
}

/**
 * Functies/classes moeten zo dom mogelijk zijn. Weg met niet weg te mocken Utility methodes!!!!
 */
private fun persistCurrentTemperature2(sensor: TemperatureSensor) {
    val roomTemperature: Temperature = sensor.currentRoomTemperature()
    TemperatureRepository()
            .persist(roomTemperature)
}

class TemperatureSensor() {
    /**
     * Returns temperature in Kelvin
     */
    fun currentTemperature(): Double {
        return 4.0
    }

    fun currentTemperatureInCelsius(): Double { // Extreme naming
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

    fun persistTemperature2(temperatureInKelvin: Double) {
        // Persisting
    }

    fun persistTemperatureInKelvin(temperature: Double) { // Extreme naming
        // Persisting
    }

    fun persist(temperature: Temperature) {
        // Persisting
    }
}


private fun persistCurrentTemperature() {
    // Mensen maken fouten, maak het moeilijk om fouten te maken
    val roomTemperature: Temperature = Temperature.of(22.0, Celsius)
    val temperatureInKelvin: Double = roomTemperature.valueIn(Kelvin)

    println("The current room temperature is $temperatureInKelvin $Kelvin")
    println("In $Fahrenheit this would be ${roomTemperature.valueIn(Fahrenheit)}")
}

private fun length() {
    val beamLength: Length = Length.of(1.0, Meter)
    val otherBeamLength: Length = Length.of(10.0, Inch)

    val totalLength: Length = beamLength + otherBeamLength

    println("The total length is ${totalLength.valueIn(Inch)} $Inch")
}

private fun length2() {
    val beamA: Length = Length.of(0.1, Meter)
    val beamB: Length = Length.of(10.0, Inch)

    when {
        beamA > beamB -> println("Beam A is longer then beam B")
        beamB >= beamA -> println("Beam B is longer or equal to beam A")
    }
}

private fun address() {
    val simpleAddress = SimpleAddress(
            city = "2624VV",
            postcode = "Delft",
            street = "Herculesweg",
            houseNumber = 123,
            houseNumberAddition = "A"
    )

    // Utility vs value objects
    val address = Address(
            "Delft",
            DutchPostcode("2624VV"),
            "Herculesweg",
            Housenumber(123, "A")
    )

    // Als alles een string is dan kan de compiler je niet helpen
//    val address2 = Address(
//            "2624VV",
//            "Delft",
//            "Herculesweg",
//            "123A"
//    )
}

fun ButtonStatus.toMuteEvent(): MuteEvent {
    return when (this) {
        ButtonStatus.PRESSED -> MuteEvent(true)
        else -> MuteEvent(false)
    }
}

class TemperatureUtil {
    companion object {
        fun kelvinToFahrenheit(value: Double): Double {
            return 1.0
        }

        fun celsiusToFahrenheit(value: Double): Double {
            return 1.0
        }
    }
}