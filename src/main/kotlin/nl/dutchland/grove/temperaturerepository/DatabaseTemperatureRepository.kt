package nl.dutchland.grove.temperaturerepository

import me.liuwj.ktorm.database.Database
import nl.dutchland.grove.temperatureandhumidity.TemperatureMeasurement

class DatabaseTemperatureRepository : TemperatureRepository {

    init {
        val database: Database = Database.connect("jdbc:h2:mem:testdb", driver = "org.h2.Driver", user = "sa")
    }


    override fun persist(measurement: TemperatureMeasurement) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}