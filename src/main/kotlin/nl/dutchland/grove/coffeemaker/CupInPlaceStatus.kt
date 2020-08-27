package nl.dutchland.grove.coffeemaker

import nl.dutchland.grove.events.EventBus
import nl.dutchland.grove.utility.mass.Kilogram
import nl.dutchland.grove.utility.mass.Mass

class CoffeePotInPlaceStatus(eventBus: EventBus) {
    private val weightOfEmptyCoffeePot = Mass.of( 10.0, Kilogram)

    init {
        eventBus.subscribe<CoffeePotHolderWeightEvent> {
            val holderIsInPlace = it.mass >=  weightOfEmptyCoffeePot * 0.8
            if (holderIsInPlace) {
                eventBus.post(CoffeePotInPlaceEvent())
            } else {
                eventBus.post(CoffeePotNotInPlaceEvent())
            }
        }
    }
}