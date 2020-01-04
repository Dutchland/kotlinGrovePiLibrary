package nl.dutchland.grove.temperaturerepository

import me.liuwj.ktorm.database.Database
import me.liuwj.ktorm.dsl.*
import me.liuwj.ktorm.entity.Entity
import me.liuwj.ktorm.entity.findAll
import me.liuwj.ktorm.schema.Table
import me.liuwj.ktorm.schema.double
import me.liuwj.ktorm.schema.int
import me.liuwj.ktorm.schema.long
import nl.dutchland.grove.temperatureandhumidity.TemperatureMeasurement
import nl.dutchland.grove.utility.TimeStamp
import nl.dutchland.grove.utility.temperature.Kelvin
import nl.dutchland.grove.utility.temperature.Temperature

class KtormTemperatureRepository(credentials: DatabaseCredentials) : TemperatureRepository {
    init {
        Database.connect(
                url = credentials.url,
                driver = credentials.driver,
                user = credentials.user)
    }

    override fun persist(measurement: TemperatureMeasurement) {
        TemperatureTable.insert {
            it.value to measurement.temperature.valueIn(Kelvin)
            it.timestamp to measurement.timestamp.millisecondsSinceEpoch
        }
    }

    private fun mapToEntity(dto: TemperatureMeasurementDto): TemperatureMeasurement {
        return TemperatureMeasurement(
                Temperature.of(dto.value, Kelvin),
                TimeStamp(dto.timestamp))
    }

    override fun all(): Collection<TemperatureMeasurement> {
        return TemperatureTable
                .findAll()
                .map { row -> mapToEntity(row) }
    }

    interface TemperatureMeasurementDto : Entity<TemperatureMeasurementDto> {
        val id: Int
        var value: Double
        var timestamp: Long
    }

    object TemperatureTable : Table<TemperatureMeasurementDto>("temperature") {
        val id by int("id").primaryKey()
                .bindTo { it.id }
        val value by double("value")
                .bindTo { it.value }
        val timestamp by long("timestamp")
                .bindTo { it.timestamp }
    }
}