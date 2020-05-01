package nl.dutchland.grove.coffeemaker

import nl.dutchland.grove.events.Event
import nl.dutchland.grove.events.EventBus
import nl.dutchland.grove.led.Led
import nl.dutchland.grove.temperatureandhumidity.TemperatureListener
import nl.dutchland.grove.utility.Fraction
import nl.dutchland.grove.utility.temperature.Temperature

val eventBus = EventBus()

val boilerWaterTemperatureSensor = TemperatureSensor
        .withListener { t -> eventBus.post(BoilerWaterTemperatureEvent(t.temperature)) }

val boilerWaterLevelSensor: WaterlevelSensor = WaterlevelSensor
        .withListener { waterLevel -> eventBus.post(BoilerWaterLevelEvent(waterLevel)) }

val cupHolderTemperatureSensor = TemperatureSensor
        .withListener { t -> eventBus.post(CupholderTemperatureEvent(t.temperature)) }

val cupHolderWeightSensor: WeightSensor = WeightSensor
        .withListener { weight -> eventBus.post(CupHolderWeightEvent(weight)) }

fun main() {
    CupInPlaceStatus(eventBus)
    CupHolderIsHotStatus(eventBus)

    val cupHolderIsHotIndicator: Led = SimpleLed()
    eventBus.subscribe<CupHolderIsHotEvent> { cupHolderIsHotIndicator.turnOn() }
    eventBus.subscribe<CupHolderIsNotHotEvent> { cupHolderIsHotIndicator.turnOff() }

    val boilerPump = BoilerPump(eventBus)

    val pump = Pump()
    eventBus.subscribe<PumpShouldBeOnEvent> { pump.turnOn() }
    eventBus.subscribe<PumpShouldBeOffEvent> { pump.turnOff() }


    val boilerHeaterElement = HeaterElement()
    val boilerHeater: BoilerHeater = BoilerHeater(eventBus)

    val coffeemakerStatus = CoffeeMakerStatus(eventBus)


    //Water heater
    // Water reservoir
    // water level sensor
    // water temperature sensor
    // water pump

    // turn coffee on button

    // cup heater
    // cup heater temperature sensor
    // cup heater is hot indicator
}

class CoffeeMakerStatus(eventBus: EventBus) {
    var waterLevel = Fraction.ZERO
    var isCupInPlace = false
    var isTurnedOn = false

    init {
        eventBus.subscribe<BoilerWaterLevelEvent> { waterLevel = it.waterLevel }
        eventBus.subscribe<CupInPlaceEvent> { isCupInPlace = true }
        eventBus.subscribe<CupNotInPlaceEvent> { isCupInPlace = false }
        eventBus.subscribe<CoffeeMakerTurnedOnEvent> { isTurnedOn = true }
        eventBus.subscribe<CoffeeMakerNotOnEvent> { isTurnedOn = false }
    }
}

class CoffeeMakerTurnedOnEvent : Event
class CoffeeMakerNotOnEvent : Event
class CupHolderIsNotHotEvent : Event
class CupHolderIsHotEvent : Event
class PumpShouldBeOffEvent : Event
class PumpShouldBeOnEvent : Event
class BoilerShouldNotBeHeatedEvent : Event
class BoilerShouldBeHeatedEvent : Event
class CupNotInPlaceEvent : Event
class CupInPlaceEvent : Event
class CupHolderWeightEvent(val weight: Double) : Event
class BoilerWaterTemperatureEvent(val temperature: Temperature) : Event
class BoilerWaterLevelEvent(val waterLevel: Fraction) : Event
class CupholderTemperatureEvent(val temperature: Temperature) : Event


class WaterlevelSensor private constructor(private val listener: (Fraction) -> Unit) {
    companion object {
        fun withListener(listener: (Fraction) -> Unit): WaterlevelSensor {
            return WaterlevelSensor(listener)
        }
    }
}

class WeightSensor private constructor(private val listener: (Double) -> Unit) {
    companion object {
        fun withListener(listener: (Double) -> Unit): WeightSensor {
            return WeightSensor(listener)
        }
    }
}

class TemperatureSensor private constructor(private val listener: TemperatureListener) {
    companion object {
        fun withListener(listener: TemperatureListener): TemperatureSensor {
            return TemperatureSensor(listener)
        }
    }
}

class HeaterElement {
    fun turnOn() {

    }

    fun turnOff() {

    }
}

class Pump {
    fun turnOn() {

    }

    fun turnOff() {

    }
}

class SimpleLed : Led {
    override fun turnOn() {
        TODO("Not yet implemented")
    }

    override fun turnOff() {
        TODO("Not yet implemented")
    }

}