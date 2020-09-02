package nl.dutchland.grove.events

import nl.dutchland.grove.utility.baseunits.temperature.Temperature

open class TemperatureEvent(
        val temperature: Temperature,
        val sensorDescription: String) : Event