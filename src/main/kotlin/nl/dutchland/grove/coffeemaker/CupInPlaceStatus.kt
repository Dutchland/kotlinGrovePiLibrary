package nl.dutchland.grove.coffeemaker

import nl.dutchland.grove.events.EventBus

class CoffeePotInPlaceStatus(eventBus: EventBus) {
    private val weightOfEmptyCoffeePot = 10.0

    init {
        eventBus.subscribe<CoffeePotHolderWeightEvent> {
            val holderIsInPlace = it.weight >= 0.8 * weightOfEmptyCoffeePot
            if (holderIsInPlace) {
                eventBus.post(CoffeePotInPlaceEvent())
            } else {
                eventBus.post(CoffeePotNotInPlaceEvent())
            }
        }
    }
}