package nl.dutchland.grove.events

import kotlin.reflect.KClass

typealias EventHandler<T> = (T) -> Unit
typealias EventFilter<T> = (T) -> Boolean

class EventBus() {
    private val eventTypeToListenersMap: MutableMap<KClass<*>, MutableCollection<EventHandler<*>>> = mutableMapOf()

    fun <T : Event> subscribe(clazz: KClass<T>, eventHandler: EventHandler<T>) {
        eventTypeToListenersMap[clazz]?.add(eventHandler)
        eventTypeToListenersMap.putIfAbsent(clazz, mutableListOf(eventHandler))
    }

    inline fun <reified T : Event> subscribe(noinline eventHandler: EventHandler<T>) {
        subscribe(T::class, eventHandler)
    }

    inline fun <reified T : Event> subscribeWithFilter(noinline filter: EventFilter<T>, noinline eventHandler: EventHandler<T>) {
        val combinedEventHandler: EventHandler<T> = {
            if (filter.invoke(it)) eventHandler.invoke(it)
        }
        subscribe(T::class, combinedEventHandler)
    }

    fun <R : Event> post(event: R) {
        eventTypeToListenersMap.entries
                .filter { it.key.java.isInstance(event) }
                .map { it.value }
                .flatten()
                .forEach { (it as EventHandler<R>).invoke(event) }
    }
}