package nl.dutchland.grove.coffeemaker

import nl.dutchland.grove.events.EventBus

class CupInPlaceStatus(eventBus: EventBus) {
    private val weightOfEmptyCup = 10.0

    init {
        eventBus.subscribe<CupHolderWeightEvent> {
            val holderIsInPlace = it.weight >= weightOfEmptyCup
            if (holderIsInPlace) {
                eventBus.post(CupInPlaceEvent())
            } else {
                eventBus.post(CupNotInPlaceEvent())
            }
        }
    }
}