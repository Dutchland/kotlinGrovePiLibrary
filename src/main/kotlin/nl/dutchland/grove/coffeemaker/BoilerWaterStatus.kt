package nl.dutchland.grove.coffeemaker

import nl.dutchland.grove.events.EventBus
import nl.dutchland.grove.utility.baseunits.temperature.Celsius
import nl.dutchland.grove.utility.baseunits.temperature.Temperature
import kotlin.properties.Delegates

class BoilerWaterStatus(private val eventBus: EventBus) {
    private var isBoilerEmpty by Delegates.observable(true)
    { _, _, _ ->
        checkWaterReady()
    }

    private var currentWaterTemperature by Delegates.observable(Temperature.of(0.0, Celsius))
    { _, _, _ ->
        checkWaterReady()
    }

    init {
        eventBus.subscribe<BoilerWaterLevelEvent> {
            this.isBoilerEmpty = it.isEmpty
        }
        eventBus.subscribe<BoilerWaterTemperatureEvent> {
            this.currentWaterTemperature = it.temperature
        }
    }

    private fun checkWaterReady() {
        if (!waterIsWarmEnough() || isBoilerEmpty) {
            eventBus.post(BoilerWaterIsNotReadyEvent())
        } else {
            eventBus.post(BoilerWaterIsReadyEvent())
        }
    }

    private fun waterIsWarmEnough() : Boolean {
        return currentWaterTemperature >= Temperature.of(90.0, Celsius)
    }
}