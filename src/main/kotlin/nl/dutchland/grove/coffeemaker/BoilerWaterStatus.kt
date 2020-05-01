package nl.dutchland.grove.coffeemaker

import nl.dutchland.grove.events.EventBus
import nl.dutchland.grove.utility.Fraction
import nl.dutchland.grove.utility.temperature.Celsius
import nl.dutchland.grove.utility.temperature.Temperature
import kotlin.properties.Delegates

class BoilerWaterStatus(private val eventBus: EventBus) {
    private var currentWaterLevel by Delegates.observable(Fraction.ZERO)
    { _, _, _ ->
        checkWaterReady()
    }

    private var currentWaterTemperature by Delegates.observable(Temperature.of(0.0, Celsius))
    { _, _, _ ->
        checkWaterReady()
    }

    init {
        eventBus.subscribe<BoilerWaterLevelEvent> {
            this.currentWaterLevel = it.waterLevel
        }
        eventBus.subscribe<BoilerWaterTemperatureEvent> {
            this.currentWaterTemperature = it.temperature
        }
    }

    private fun checkWaterReady() {
        if (!waterIsWarmEnough() || boilerIsEmpty()) {
            eventBus.post(BoilerWaterIsNotReadyEvent())
        } else {
            eventBus.post(BoilerWaterIsReadyEvent())
        }
    }

    private fun boilerIsEmpty(): Boolean {
        return currentWaterLevel == Fraction.ZERO
    }

    private fun waterIsWarmEnough() : Boolean {
        return currentWaterTemperature >= Temperature.of(90.0, Celsius)
    }
}