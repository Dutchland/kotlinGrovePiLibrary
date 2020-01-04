package nl.dutchland.grove.temperaturerepository

import nl.dutchland.grove.temperatureandhumidity.TemperatureMeasurement

interface TemperatureRepository {
    fun persist(measurement: TemperatureMeasurement)
}