package nl.dutchland.grove.events

import nl.dutchland.grove.temperatureandhumidity.TemperatureHumidityMeasurement
import nl.dutchland.grove.utility.RelativeHumidity
import nl.dutchland.grove.utility.temperature.Temperature

open class TemperatureHumidityEvent(
        val measurement: TemperatureHumidityMeasurement,
        val sensorDescription: String) : Event