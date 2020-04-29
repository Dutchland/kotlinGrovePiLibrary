package nl.dutchland.grove

import nl.dutchland.grove.utility.temperature.Temperature
import kotlin.reflect.KClass

typealias EventHandler<T> = (T) -> Unit
typealias EventFilter<T> = (T) -> Boolean

interface Event {

}

class SomeOtherEvent : Event

class TemperatureEvent(val temperature: Temperature) : Event

class EventBus() {
    private val listeners = mutableListOf<EventListener<*>>()

    fun <T : Event> subscribe(eventListener: EventListener<T>) {
        listeners += (eventListener)
    }

    fun <T : Event> subscribe(eventType: KClass<T>, eventHandler: EventHandler<T>) {
        val bla = eventHandler
        subscribe<T>(EventListener(forType(eventType), eventHandler, eventType))
    }

    fun post(event: Event) {
        listeners.forEach { eventListener ->
            eventListener.invoke(event)
        }
    }
}

fun <T : Event> forType(eventType: KClass<T>): EventFilter<T> {
    return { event -> event::class == eventType }
}


class EventListener<T : Event>(
        private val eventFilter: EventFilter<T>,
        private val eventHandler: EventHandler<T>,
        private val clazz: KClass<T>) {

//    constructor(eventHandler: EventHandler<T>) : this({ e: Event -> true }, eventHandler)

    fun invoke(event: Event) {
        val isCorrectType = clazz == event::class
        if (isCorrectType && eventFilter.invoke(event as T)) {
            eventHandler.invoke(event)
        }
    }
}