package nl.dutchland.grove.demo

import nl.dutchland.grove.GrovePi4J

import nl.dutchland.grove.buzzer.Buzzer
import nl.dutchland.grove.buzzer.GroveBuzzer
import nl.dutchland.grove.buzzer.GroveBuzzerFactory
import nl.dutchland.grove.events.EventBus
import nl.dutchland.grove.events.TemperatureEvent
import nl.dutchland.grove.grovepiports.GrovePi
import nl.dutchland.grove.temperatureandhumidity.*
import nl.dutchland.grove.temperatureandhumidity.GroveTemperatureHumiditySensors.*
import nl.dutchland.grove.temperatureandhumidity.TemperatureSensor
import nl.dutchland.grove.utility.temperature.Celsius
import nl.dutchland.grove.utility.temperature.Temperature
import org.iot.raspberry.grovepi.devices.GroveLed

const val A2_DIGITAL_PORT = 16
const val A2_ANALOG_PORT = 2

fun main() {
    UnsafeSafeBuzzerFactory(GrovePi4J())
            .onPort(2)

    UnsafeSafeBuzzerFactory(GrovePi4J())
            .onPort(A2_ANALOG_PORT)

    // Gebruiker moet weten dat het om de digitale port gaat
    UnsafeSafeBuzzerFactory(GrovePi4J())
            .onDigitalPort(A2_ANALOG_PORT)

    val somePort = -10
    UnsafeSafeBuzzerFactory(GrovePi4J())
            .onDigitalPort(somePort)

    // Gevraagde port nummer mag niet elke integer zijn
    // --> Eigen type


    // Geen zorgen meer over Digitale of Analoge poort. Compiler knows best.
    // Geen validatie op poort nodig --> geen test nodig
    val buzzerFactory = GroveBuzzerFactory(GrovePi4J())
    val buzzer: Buzzer = buzzerFactory
            .on(GrovePi.A2)

    // Autocomplete
    val adjustableBuzzer = buzzerFactory
            .adjustableBuzzerOn(GrovePi.D4)
}


/**
 * Builders hebben normaal als nadeel dat je setters kan vergeten aan te roepen
 */
fun temperature() {
    val eventBus = EventBus()

    val temperatureSensor: TemperatureSensor = TemperatureSensorBuilder(GrovePi4J())
            .onPort(GrovePi.D4)
            .withType(DHT11)
            .withTemperatureListener { measurement -> eventBus.post(measurement.toEvent()) }
            .build()

    temperatureSensor.start()

    val temperatureRepository = TemperatureRepository()
    eventBus.subscribe<TemperatureEvent> { temperatureRepository.persist(it.temperature) }
    eventBus.subscribeWithFilter<TemperatureEvent>(
            filter = { it.temperature > Temperature.of(100.0, Celsius) },
            eventHandler = { println("Warning high temperature!!!!") })
}


class UnsafeSafeBuzzerFactory(grovePi4J: GrovePi4J) {

    /**
     * Digital port
     */
    fun onPort(port: Int): Buzzer {
        return GroveBuzzer(
                GroveLed(GrovePi4J(), port))
    }

    fun onDigitalPort(port: Int): Buzzer {
        return GroveBuzzer(
                GroveLed(GrovePi4J(), port))
    }

}

fun TemperatureMeasurement.toEvent(): TemperatureEvent {
    return TemperatureEvent(this.temperature, "DHT11")
}
