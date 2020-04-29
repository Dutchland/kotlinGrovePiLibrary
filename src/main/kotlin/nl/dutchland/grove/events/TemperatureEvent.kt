package nl.dutchland.grove.events

import nl.dutchland.grove.utility.temperature.Temperature

open class TemperatureEvent(
        val temperature: Temperature,
        val sensorDescription: String) : Event