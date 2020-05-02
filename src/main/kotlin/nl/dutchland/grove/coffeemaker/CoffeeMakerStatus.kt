package nl.dutchland.grove.coffeemaker

import nl.dutchland.grove.events.EventBus
import kotlin.properties.Delegates.observable

class CoffeeMakerStatus(private val eventBus: EventBus) {
    private var isBoilerEmpty by observable(true)
    { _, oldValue, _ ->
        val boilerWasFullButIsEmptyNow = !oldValue
        if (boilerWasFullButIsEmptyNow) turnOffCoffeeMaker()

    }
    private var isCoffeePotInPlace by observable(false)
    { _, oldValue, _ ->
        val coffeePotWasInPlaceButIsNotAnymore = oldValue
        if (coffeePotWasInPlaceButIsNotAnymore) turnOffCoffeeMaker()
    }

    init {
        eventBus.subscribe<BoilerWaterLevelEvent> { isBoilerEmpty = it.isEmpty }
        eventBus.subscribe<CoffeePotInPlaceEvent> { isCoffeePotInPlace = true }
        eventBus.subscribe<CoffeePotNotInPlaceEvent> { isCoffeePotInPlace = false }
        eventBus.subscribe<TryToTurnOnCoffeeMakerEvent> { tryToTurnOnCoffeeMaker() }
    }

    private fun tryToTurnOnCoffeeMaker() {
        if (isCoffeePotInPlace && !isBoilerEmpty) {
            turnOnCoffeeMaker()
        } else {
            turnOffCoffeeMaker()
        }
    }

    private fun turnOnCoffeeMaker() {
        eventBus.post(CoffeeMakerTurnedOnEvent())
    }

    private fun turnOffCoffeeMaker() {
        val reasons = mutableListOf<String>()

        if (isBoilerEmpty) {
            reasons += "there is no water in the tank"
        }

        if (!isCoffeePotInPlace) {
            reasons += "the coffeepot is not in place"
        }

        eventBus.post(CoffeeMakerTurnedOffEvent(reasons))
    }
}