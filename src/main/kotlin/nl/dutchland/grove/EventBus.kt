package nl.dutchland.grove

import nl.dutchland.grove.utility.temperature.Temperature
import kotlin.reflect.KClass

typealias EventHandler<T> = (T) -> Unit
typealias EventFilter<T> = (T) -> Boolean

interface Event

class SomeOtherEvent : Event

open class TemperatureEvent(val temperature: Temperature, val sensorDescription: String)
    : Event

class Dht111TemperatureEvent(temperature: Temperature)
    : TemperatureEvent(temperature, "DHT111")

class EventBus() {
    private val listeners = mutableListOf<EventListener<*>>()

    fun <T : Event> subscribe(eventListener: EventListener<T>) {
        listeners += (eventListener)
    }

    fun <T : Event> subscribeToType(eventType: KClass<T>, eventHandler: EventHandler<T>) {
        subscribe<T>(EventListener(eventType, forType(eventType), eventHandler))
    }

    inline fun <reified T : Event> subscribe(noinline eventHandler: EventHandler<T>) {
        val eventType = T::class
        subscribe(EventListener(eventType, forType(eventType), eventHandler))
    }

    inline fun <reified T : Event> subscribeWithFilter(noinline filter: EventFilter<T>, noinline eventHandler: EventHandler<T>) {
        subscribe(EventListener(T::class, filter, eventHandler))
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

class EventListener<out T : Event>(
        private val clazz: KClass<T>,
        private val eventFilter: EventFilter<T>,
        private val eventHandler: EventHandler<T>) {

    fun invoke(event: Event) {
        val isCorrectType =  clazz.java.isInstance(event)
        if (isCorrectType && eventFilter.invoke(event as T)) {
            eventHandler.invoke(event)
        }
    }
}