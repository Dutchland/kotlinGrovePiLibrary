package nl.dutchland.grove.events

import kotlin.reflect.KClass

data class EventListener<out T : Event>(
        private val clazz: KClass<T>,
        private val eventFilter: EventFilter<T>,
        private val eventHandler: EventHandler<T>) {

    fun onEvent(event: Event) {
        val isCorrectType =  clazz.java.isInstance(event)
        if (isCorrectType && eventFilter.invoke(event as T)) {
            eventHandler.invoke(event)
        }
    }
}