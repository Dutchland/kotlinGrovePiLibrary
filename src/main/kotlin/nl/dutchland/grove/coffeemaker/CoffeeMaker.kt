package nl.dutchland.grove.coffeemaker

import nl.dutchland.grove.InputDevice
import nl.dutchland.grove.OutputDevice
import nl.dutchland.grove.button.Button
import nl.dutchland.grove.button.ButtonStatus
import nl.dutchland.grove.events.Event
import nl.dutchland.grove.events.EventBus
import nl.dutchland.grove.led.Led
import nl.dutchland.grove.utility.Fraction
import nl.dutchland.grove.utility.temperature.Temperature

val eventBus = EventBus()

val boilerWaterTemperatureSensor = TemperatureSensor
        .withListener { t -> eventBus.post(BoilerWaterTemperatureEvent(t.temperature)) }

val BOILER_WATER_LEVEL_SENSOR: WaterLevelSensor = WaterLevelSensor
        .withListener { waterLevel -> eventBus.post(BoilerWaterLevelEvent(waterLevel)) }

val cupHolderTemperatureSensor = TemperatureSensor
        .withListener { t -> eventBus.post(CupholderTemperatureEvent(t.temperature)) }

val cupHolderWeightSensor: WeightSensor = WeightSensor
        .withListener { weight -> eventBus.post(CupHolderWeightEvent(weight)) }

val turnCoffeeMachineOnButton: Button = SimpleButton.withListener {
    if (it == ButtonStatus.PRESSED) {
        eventBus.post(CoffeeMakerTurnedOnEvent())
    }
}


val inputDevices: Collection<InputDevice> = listOf(
        boilerWaterTemperatureSensor,
        BOILER_WATER_LEVEL_SENSOR,
        cupHolderTemperatureSensor,
        cupHolderWeightSensor,
        turnCoffeeMachineOnButton)

val pump = Pump()
val cupHolderIsHotIndicator: Led = SimpleLed()
val cupHolderHeaterElement = HeaterElement()
val boilerHeaterElement = HeaterElement()

val outputDevices: Collection<OutputDevice> = listOf(
        cupHolderIsHotIndicator,
        pump,
        boilerHeaterElement,
        cupHolderHeaterElement)

fun main() {
    CoffeeMakerStatus(eventBus)
    CupInPlaceStatus(eventBus)
    CupHolderIsHotStatus(eventBus)
    CoffeeMakerStatus(eventBus)
    BoilerWaterTemperatureStatus(eventBus)
    BoilerWaterStatus(eventBus)

    eventBus.subscribe<CupHolderIsHotEvent> {
        cupHolderIsHotIndicator.turnOn()
    }
    eventBus.subscribe<CupHolderIsNotHotEvent> {
        cupHolderIsHotIndicator.turnOff()
    }

    eventBus.subscribe<BoilerWaterIsReadyEvent> {
        pump.turnOn()
    }
    eventBus.subscribe<BoilerWaterIsNotReadyEvent> {
        pump.turnOff()
    }

    eventBus.subscribe<BoilerShouldBeHeatedEvent> {
        boilerHeaterElement.turnOn()
    }
    eventBus.subscribe<BoilerShouldNotBeHeatedEvent> {
        boilerHeaterElement.turnOff()
    }

    startCoffeeMaker()

    stopCoffeeMaker()
}

fun startCoffeeMaker() {
    inputDevices.forEach { it.start() }
}

private fun stopCoffeeMaker() {
    inputDevices.forEach { it.stop() }
    outputDevices.forEach { it.stop() }
}


class CoffeeMakerTurnedOnEvent : Event

class CupHolderIsNotHotEvent : Event
class CupHolderIsHotEvent : Event

class BoilerWaterIsNotReadyEvent : Event
class BoilerWaterIsReadyEvent : Event

class BoilerShouldNotBeHeatedEvent : Event
class BoilerShouldBeHeatedEvent : Event

class CupNotInPlaceEvent : Event
class CupInPlaceEvent : Event

class CupHolderWeightEvent(val weight: Double) : Event
class BoilerWaterTemperatureEvent(val temperature: Temperature) : Event
class BoilerWaterLevelEvent(val waterLevel: Fraction) : Event
class CupholderTemperatureEvent(val temperature: Temperature) : Event