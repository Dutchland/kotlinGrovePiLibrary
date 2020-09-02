package nl.dutchland.grove.events

import nl.dutchland.grove.temperatureandhumidity.TemperatureHumidityMeasurement

open class TemperatureHumidityEvent(
        val measurement: TemperatureHumidityMeasurement,
        val sensorDescription: String) : Event