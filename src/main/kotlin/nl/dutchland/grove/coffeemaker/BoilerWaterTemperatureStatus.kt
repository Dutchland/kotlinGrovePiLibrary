package nl.dutchland.grove.coffeemaker

import nl.dutchland.grove.events.EventBus
import nl.dutchland.grove.utility.temperature.Celsius
import nl.dutchland.grove.utility.temperature.Temperature
import kotlin.properties.Delegates

class BoilerWaterTemperatureStatus(private val eventBus: EventBus) {
    private var boilerWaterTemperature: Temperature by Delegates.observable(Temperature.of(0.0, Celsius))
    { _, _, _ ->
        check()
    }

    init {
        eventBus.subscribe<BoilerWaterTemperatureEvent> { this.boilerWaterTemperature = it.temperature }
    }

    private fun check() {
        if (waterNeedsHeating()) {
            eventBus.post(BoilerShouldBeHeatedEvent())
        } else {
            eventBus.post(BoilerShouldNotBeHeatedEvent())
        }
    }

    private fun waterNeedsHeating(): Boolean {
        return boilerWaterTemperature < Temperature.of(95.0, Celsius)
    }
}