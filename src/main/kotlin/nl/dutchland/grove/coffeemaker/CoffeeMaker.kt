package nl.dutchland.grove.coffeemaker

import nl.dutchland.grove.events.Event
import nl.dutchland.grove.events.EventBus
import nl.dutchland.grove.led.Led
import nl.dutchland.grove.temperatureandhumidity.TemperatureListener
import nl.dutchland.grove.utility.Fraction
import nl.dutchland.grove.utility.temperature.Celsius
import nl.dutchland.grove.utility.temperature.Temperature
import kotlin.properties.Delegates

fun main() {
    val eventBus = EventBus()

    val boilerWaterTemperatureSensor = TemperatureSensor
            .withListener { t -> eventBus.post(BoilerWaterTemperatureEvent(t.temperature)) }

    val cupHolderTemperatureSensor = TemperatureSensor
            .withListener { t -> eventBus.post(CupholderTemperatureEvent(t.temperature)) }

    val boilerWaterLevelSensor: WaterlevelSensor = WaterlevelSensor
            .withListener { waterLevel -> eventBus.post(WaterLevelEvent(waterLevel)) }

    val weightOfEmptyCup = 10.0
    val cupInPlaceSensor: WeightSensor = WeightSensor
            .withListener { weight ->
                when {
                    weight >= weightOfEmptyCup -> eventBus.post(CupInPlaceEvent())
                    else -> eventBus.post(CupNotInPlaceEvent())
                }
            }

    val cupHolderIsHotIndicator: Led = SimpleLed()
    eventBus.subscribe<CupholderTemperatureEvent> { tm ->
        when {
            tm.cupholderTemperature > Temperature.of(50.0, Celsius) -> cupHolderIsHotIndicator.turnOn()
            else -> cupHolderIsHotIndicator.turnOff()
        }
    }

    val boilerPump: BoilerPump = BoilerPump(Pump(), eventBus)

    val boilerHeater: BoilerHeater = BoilerHeater(HeaterElement(), eventBus)

    eventBus.subscribe<BoilerWaterTemperatureEvent> { t ->

    }


    //Water heater
    // Water reservoir
    // water level sensor
    // water temperature sensor
    // water pump

    // turn coffee on button


    // cup heater weight sensor (dont turn heater on if there is no water in there or cup is not in place
    // cup is in place sensor (needed icw cup heater weight sensor ??)
    // cup heater
    // cup heater temperature sensor
    // cup heater is hot indicator
}

class BoilerPump(private val pump: Pump, eventBus: EventBus) {
    private var currentWaterLevel by Delegates.observable(Fraction.ZERO)
    { _, _, _ ->
        checkTurningPumpOn()
    }

    init {
        eventBus.subscribe<WaterLevelEvent> { measurement ->
            this.currentWaterLevel = measurement.waterLevel
        }
    }

    private fun checkTurningPumpOn() {
        if (currentWaterLevel > Fraction.ZERO) {
            pump.turnOn()
        } else {
            pump.turnOff()
        }
    }

    private fun isWaterEmpty(): Boolean {
        return currentWaterLevel == Fraction.ZERO
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

class BoilerHeater(private val heaterElement: HeaterElement, eventBus: EventBus) {
    val boilerWaterTemperature: Temperature = Temperature.of(0.0, Celsius)

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

class CupNotInPlaceEvent : Event

class CupInPlaceEvent : Event

class WeightSensor(val listener: (Double) -> Unit) {
    companion object {
        fun withListener(listener: (Double) -> Unit): WeightSensor {
            return WeightSensor(listener)
        }
    }
}

class WaterlevelSensor(val listener: (Fraction) -> Unit) {
    companion object {
        fun withListener(listener: (Fraction) -> Unit): WaterlevelSensor {
            return WaterlevelSensor(listener)
        }
    }
}

class BoilerWaterTemperatureEvent(val waterTemperature: Temperature) : Event
class WaterLevelEvent(val waterLevel: Fraction) : Event
class CupholderTemperatureEvent(val cupholderTemperature: Temperature) : Event

class TemperatureSensor private constructor(val listener: TemperatureListener) {
    companion object {
        fun withListener(listener: TemperatureListener): TemperatureSensor {
            return TemperatureSensor(listener)
        }
    }
}