package nl.dutchland.grove.coffeemaker

import nl.dutchland.grove.InputDevice
import nl.dutchland.grove.OutputDevice
import nl.dutchland.grove.button.Button
import nl.dutchland.grove.button.ButtonStatus
import nl.dutchland.grove.coffeemaker.sensors.*
import nl.dutchland.grove.events.Event
import nl.dutchland.grove.events.EventBus
import nl.dutchland.grove.led.Led
import nl.dutchland.grove.utility.Fraction
import nl.dutchland.grove.utility.temperature.Temperature
import nl.dutchland.grove.utility.mass.Mass

private val eventBus = EventBus()

private val boilerWaterTemperatureSensor = TemperatureSensor
        .withListener { measurement -> eventBus.post(BoilerWaterTemperatureEvent(measurement.temperature)) }

private val boilerWaterLevelSensor: WaterLevelSensor = WaterLevelSensor
        .withListener { waterLevel -> eventBus.post(BoilerWaterLevelEvent(waterLevel)) }

private val cupHolderTemperatureSensor = TemperatureSensor
        .withListener { measurement -> eventBus.post(CoffeePotholderTemperatureEvent(measurement.temperature)) }

private val cupHolderWeightSensor: WeightSensor = WeightSensor
        .withListener { weight -> eventBus.post(CoffeePotHolderWeightEvent(weight)) }

private val coffeeMachineOnButton: Button = SimpleButton
        .withStatusChangedListener { if (it == ButtonStatus.PRESSED) eventBus.post(TryToTurnOnCoffeeMakerEvent()) }

private val inputDevices: Collection<InputDevice> = listOf(
        boilerWaterTemperatureSensor,
        boilerWaterLevelSensor,
        cupHolderTemperatureSensor,
        cupHolderWeightSensor,
        coffeeMachineOnButton)

private val pump = Pump()
private val cupHolderIsHotIndicator: Led = SimpleLed()
private val cupHolderHeaterElement = HeaterElement()
private val boilerHeaterElement = HeaterElement()

private val outputDevices: Collection<OutputDevice> = listOf(
        cupHolderIsHotIndicator,
        pump,
        boilerHeaterElement,
        cupHolderHeaterElement)

fun main() {
    CoffeeMakerStatus(eventBus)
    CoffeePotInPlaceStatus(eventBus)
    CoffeePotHolderIsHotStatus(eventBus)
    BoilerWaterTemperatureStatus(eventBus)
    BoilerWaterStatus(eventBus)

    eventBus.subscribe<CoffeePotHolderIsHotEvent> {
        cupHolderIsHotIndicator.turnOn()
    }
    eventBus.subscribe<CoffeePotHolderIsNotHotEvent> {
        cupHolderIsHotIndicator.turnOff()
    }

    eventBus.subscribe<BoilerWaterIsReadyEvent> {
        pump.turnOn()
    }
    eventBus.subscribe<BoilerWaterIsNotReadyEvent> {
        pump.turnOff()
    }

    eventBus.subscribe<WaterIsToColdToMakeCoffee> {
        boilerHeaterElement.turnOn()
    }
    eventBus.subscribe<WaterTemperatureIsGoodForMakingCoffee> {
        boilerHeaterElement.turnOff()
    }

    eventBus.subscribe<CoffeePotHolderShouldBeHeatedEvent> {
        cupHolderHeaterElement.turnOn()
    }
    eventBus.subscribe<CoffeePotHolderShouldNotBeHeatedEvent> {
        cupHolderHeaterElement.turnOff()
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


class TryToTurnOnCoffeeMakerEvent : Event

class CoffeeMakerTurnedOnEvent : Event

class CoffeePotHolderIsNotHotEvent : Event
class CoffeePotHolderIsHotEvent : Event

class BoilerWaterIsNotReadyEvent : Event
class BoilerWaterIsReadyEvent : Event

class WaterTemperatureIsGoodForMakingCoffee : Event
class WaterIsToColdToMakeCoffee : Event

class CoffeePotHolderShouldNotBeHeatedEvent : Event
class CoffeePotHolderShouldBeHeatedEvent : Event

class CoffeePotNotInPlaceEvent : Event
class CoffeePotInPlaceEvent : Event

class CoffeePotHolderWeightEvent(val mass: Mass) : Event
class BoilerWaterTemperatureEvent(val temperature: Temperature) : Event
class BoilerWaterLevelEvent(val waterLevel: Fraction) : Event {
    val isEmpty = waterLevel == Fraction.ZERO
}
class CoffeePotholderTemperatureEvent(val temperature: Temperature) : Event

class CoffeeMakerTurnedOffEvent(val reasons: Collection<String>) : Event {
    val fullMessage = "Turned of coffeemaker because: ${reasons.joinToString()}"
}