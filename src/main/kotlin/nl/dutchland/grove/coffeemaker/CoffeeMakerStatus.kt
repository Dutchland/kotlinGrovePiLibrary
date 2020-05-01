package nl.dutchland.grove.coffeemaker

import nl.dutchland.grove.events.EventBus
import nl.dutchland.grove.utility.Fraction

class CoffeeMakerStatus(eventBus: EventBus) {
    var waterLevel = Fraction.ZERO
    var isCupInPlace = false

    init {
        eventBus.subscribe<BoilerWaterLevelEvent> { waterLevel = it.waterLevel }
        eventBus.subscribe<CupInPlaceEvent> { isCupInPlace = true }
        eventBus.subscribe<CupNotInPlaceEvent> { isCupInPlace = false }
        eventBus.subscribe<CoffeeMakerTurnedOnEvent> { tryToTurnOnCoffeeMaker() }
    }

    private fun tryToTurnOnCoffeeMaker() {
        if (isCupInPlace && waterLevel > Fraction.ZERO) {
            eventBus.post(CoffeeMakerTurnedOnEvent())
        } else {

        }
    }
}