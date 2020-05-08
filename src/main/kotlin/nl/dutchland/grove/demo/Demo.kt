package nl.dutchland.grove.demo

import nl.dutchland.grove.TemperatureSensor
import nl.dutchland.grove.TemperatureUtil
import nl.dutchland.grove.button.ButtonStatus
import nl.dutchland.grove.events.MuteEvent
import nl.dutchland.grove.utility.length.Inch
import nl.dutchland.grove.utility.length.Length
import nl.dutchland.grove.utility.length.Meter
import nl.dutchland.grove.utility.temperature.Celsius
import nl.dutchland.grove.utility.temperature.Fahrenheit
import nl.dutchland.grove.utility.temperature.Kelvin
import nl.dutchland.grove.utility.temperature.Temperature

fun main() {

}

private fun temperature0(sensor: TemperatureSensor) {
    // Temperature in what scale?
    val roomTemperature: Double = sensor.currentTemperature()
    println("Temperature in Kelvin, Celsius, Fahrenheit or some other scale?????")

    persistTemperature(
            TemperatureUtil.kelvinToFahrenheit(roomTemperature))
}

fun ButtonStatus.toMuteEvent(): MuteEvent {
    return when (this) {
        ButtonStatus.PRESSED -> MuteEvent(true)
        else -> MuteEvent(false)
    }
}


private fun storeTemperature(temperature: Temperature) {
    println("Temperature of ${temperature.valueIn(Celsius)} $Celsius")
}

class TemperatureSensor() {
    /**
     * Return temperature in Kelvin
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

/**
 * Temperature in Kelvin
 */
fun persistTemperature(temperature: Double) {
    // Persisting
}

fun persistTemperature2(temperatureInKelvin: Double) {
    // Persisting
}

fun persistTemperatureInKelvin(temperature: Double) { // Extreme naming
    // Persisting
}

fun persistTemperature(temperature: Temperature) {
    // Persisting
}

private fun temperature() {
    // Mensen maken fouten, maak het moeilijk om fouten te maken
    val roomTemperature: Temperature = Temperature.of(22.0, Celsius)
    val temperatureInKelvin: Double = roomTemperature.valueIn(Kelvin)

    println("The current room temperature is $temperatureInKelvin $Kelvin")
    println("In $Fahrenheit this would be ${roomTemperature.valueIn(Fahrenheit)}")
}

private fun temperature(preferredTemperatureScale: Temperature.Scale, sensor: TemperatureSensor) {
    val roomTemperature: Temperature = sensor.currentRoomTemperature()
    println("The current room temperature is " +
            "${roomTemperature.valueIn(preferredTemperatureScale)} $preferredTemperatureScale")

    persistTemperature(roomTemperature)
}

private fun getCurrentRoomTemperatureFromSensor(): Temperature {
    return Temperature.of(22.0, Celsius)
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