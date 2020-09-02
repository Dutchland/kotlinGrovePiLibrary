package nl.dutchland.grove.coffeemaker

import nl.dutchland.grove.events.EventBus
import nl.dutchland.grove.utility.baseunits.temperature.Celsius
import nl.dutchland.grove.utility.baseunits.temperature.Temperature

class CoffeePotHolderIsHotStatus(eventBus: EventBus) {
    init {
        eventBus.subscribe<CoffeePotholderTemperatureEvent> { tm ->
            val cupIsHot = tm.temperature > Temperature.of(50.0, Celsius)

            if (cupIsHot) {
                eventBus.post(CoffeePotHolderIsHotEvent())
            } else {
                eventBus.post(CoffeePotHolderIsNotHotEvent())
            }
        }
    }
}