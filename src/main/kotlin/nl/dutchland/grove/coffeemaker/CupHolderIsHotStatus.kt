package nl.dutchland.grove.coffeemaker

import nl.dutchland.grove.events.EventBus
import nl.dutchland.grove.utility.temperature.Celsius
import nl.dutchland.grove.utility.temperature.Temperature

class CupHolderIsHotStatus(eventBus: EventBus) {
    init {
        eventBus.subscribe<CupholderTemperatureEvent> { tm ->
            val cupIsHot = tm.temperature > Temperature.of(50.0, Celsius)

            if (cupIsHot) {
                eventBus.post(CupHolderIsHotEvent())
            } else {
                eventBus.post(CupHolderIsNotHotEvent())
            }
        }
    }
}