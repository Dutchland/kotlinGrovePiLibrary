package nl.dutchland.grove.temperaturerepository

import liquibase.Liquibase
import liquibase.database.DatabaseFactory
import liquibase.database.jvm.JdbcConnection
import liquibase.resource.FileSystemResourceAccessor
import nl.dutchland.grove.temperatureandhumidity.TemperatureMeasurement
import nl.dutchland.grove.utility.TimeStamp
import nl.dutchland.grove.utility.temperature.Temperature
import java.sql.DriverManager
import kotlin.test.Test
import kotlin.test.assertEquals

class KtormTemperatureRepositoryTest {
    @Test
    fun testSave() {
        val repository = KtormTemperatureRepository(DatabaseCredentials(
                "jdbc:h2:mem:testdb",
                "org.h2.Driver",
                "",
                ""))

        migrateDatabase()
//        repository.all()
        repository.persist(TemperatureMeasurement(Temperature.ABSOLUTE_ZERO, TimeStamp.now()))

        assertEquals(1, repository.all().size)
    }

    fun migrateDatabase() {
        val sqlConnection = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "")
        val database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(JdbcConnection(sqlConnection))
        val liquibase = Liquibase("src/main/resources/db-changelog.xml", FileSystemResourceAccessor(), database)
        liquibase.update("main")
    }
}