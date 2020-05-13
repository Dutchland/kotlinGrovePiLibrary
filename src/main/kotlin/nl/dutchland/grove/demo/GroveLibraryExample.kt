package nl.dutchland.grove.demo

import nl.dutchland.grove.GrovePi4J

import nl.dutchland.grove.buzzer.Buzzer
import nl.dutchland.grove.buzzer.GroveBuzzer
import nl.dutchland.grove.buzzer.GroveBuzzerFactory
import nl.dutchland.grove.events.EventBus
import nl.dutchland.grove.events.TemperatureEvent
import nl.dutchland.grove.grovepiports.GrovePi
import nl.dutchland.grove.grovepiports.GrovePiZero
import nl.dutchland.grove.temperatureandhumidity.*
import nl.dutchland.grove.temperatureandhumidity.GroveTemperatureHumiditySensors.*
import nl.dutchland.grove.temperatureandhumidity.TemperatureSensor
import org.iot.raspberry.grovepi.devices.GroveLed

const val A2_DIGITAL_PORT = 16
const val A2_ANALOG_PORT = 2

fun main() {
    UnsafeSafeBuzzerFactory(GrovePi4J())
            .on(A2_DIGITAL_PORT)

    val somePort = -10
    UnsafeSafeBuzzerFactory(GrovePi4J())
            .on(somePort)

    // Gevraagde port nummer != mag niet elke integer zijn
    // --> Eigen type


    val buzzerFactory = GroveBuzzerFactory(GrovePi4J())
    val buzzer: Buzzer = buzzerFactory
            .on(GrovePiZero.A2)

    val adjustableBuzzer = buzzerFactory
            .adjustableBuzzerOn(GrovePi.D4)
}


/**
 * Builders hebben normaal als nadeel dat je setters kan vergeten aan te roepen
 */
fun temperature() {
    val eventBus = EventBus()

    val temperatureSensor : TemperatureSensor = GroveTemperatureHumiditySensorBuilder(GrovePi4J())
            .onPort(GrovePi.D4)
            .withType(DHT11)
            .withTemperatureListener {  measurement -> eventBus.post(measurement.toEvent()) }
            .build()

    temperatureSensor.start()

    val temperatureRepository = TemperatureRepository()
    eventBus.subscribe<TemperatureEvent> { temperatureRepository.persist(it.temperature) }
}


fun TemperatureMeasurement.toEvent(): TemperatureEvent {
    return TemperatureEvent(this.temperature, "DHT11")
}

class UnsafeSafeBuzzerFactory(grovePi4J: GrovePi4J) {
    /**
     * Digital port
     */
    fun on(port: Int): Buzzer {
        return GroveBuzzer(
                GroveLed(GrovePi4J(), port))
    }
}