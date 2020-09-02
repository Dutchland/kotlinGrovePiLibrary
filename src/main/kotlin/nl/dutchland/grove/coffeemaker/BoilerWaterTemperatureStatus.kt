package nl.dutchland.grove.coffeemaker

import nl.dutchland.grove.events.EventBus
import nl.dutchland.grove.utility.baseunits.temperature.Celsius
import nl.dutchland.grove.utility.baseunits.temperature.Temperature
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
            eventBus.post(WaterIsToColdToMakeCoffee())
        } else {
            eventBus.post(WaterTemperatureIsGoodForMakingCoffee())
        }
    }

    private fun waterNeedsHeating(): Boolean {
        return boilerWaterTemperature < Temperature.of(95.0, Celsius)
    }
}