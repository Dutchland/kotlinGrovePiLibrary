//package nl.dutchland.grove.temperaturerepository
//
//import me.liuwj.ktorm.database.Database
//import me.liuwj.ktorm.dsl.*
//import me.liuwj.ktorm.entity.Entity
//import me.liuwj.ktorm.entity.findAll
//import me.liuwj.ktorm.schema.Table
//import me.liuwj.ktorm.schema.double
//import me.liuwj.ktorm.schema.int
//import me.liuwj.ktorm.schema.long
//import nl.dutchland.grove.temperatureandhumidity.TemperatureMeasurement
//import nl.dutchland.grove.utility.TimeStamp
//import nl.dutchland.grove.utility.temperature.Kelvin
//import nl.dutchland.grove.utility.temperature.Temperature
//
//class KtormTemperatureRepository(credentials: DatabaseCredentials) : TemperatureRepository {
//    private val database: Database = Database.connect(
//            url = credentials.url,
//            driver = credentials.driver)
//
//    override fun persist(measurement: TemperatureMeasurement) {
//        TemperatureTable.insert {
//            it.value to measurement.temperature.valueIn(Kelvin)
//            it.timestamp to measurement.timestamp.millisecondsSinceEpoch
//        }
//    }
//
//    override fun all(): Collection<TemperatureMeasurement> {
//        return TemperatureTable
//                .findAll()
//                .map { t -> t.toEntity() }
//    }
//
//    interface TemperatureMeasurementDto : Entity<TemperatureMeasurementDto> {
//        val id: Int
//        var value: Double
//        var timestamp: Long
//
//        fun toEntity(): TemperatureMeasurement {
//            return TemperatureMeasurement(
//                    Temperature.of(this.value, Kelvin),
//                    TimeStamp(this.timestamp))
//        }
//    }
//
//    object TemperatureTable : Table<TemperatureMeasurementDto>("t_temperature") {
//        val id by int("id").primaryKey()
//                .bindTo { it.id }
//        val value by double("value")
//                .bindTo { it.value }
//        val timestamp by long("timestamp")
//                .bindTo { it.timestamp }
//    }
//}