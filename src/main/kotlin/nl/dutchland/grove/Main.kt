package nl.dutchland.grove

import liquibase.Liquibase
import liquibase.database.DatabaseFactory
import liquibase.database.jvm.JdbcConnection
import liquibase.resource.FileSystemResourceAccessor
import me.liuwj.ktorm.database.use
import nl.dutchland.grove.button.ButtonStatus
import nl.dutchland.grove.button.ButtonStatus.*
import nl.dutchland.grove.button.ButtonStatusChangedListener
import nl.dutchland.grove.grovepiports.GrovePiZero
import nl.dutchland.grove.led.DimmableLed
import nl.dutchland.grove.led.Led
import nl.dutchland.grove.rgblcd.GroveLcd
import nl.dutchland.grove.rgblcd.BackgroundColor
import nl.dutchland.grove.utility.Fraction
import nl.dutchland.grove.utility.RelativeHumidity
import java.sql.DriverManager

fun main(vararg args: String) {
//    fixedRateTimer("Calling listener", false, 0, 1000)
//    {
//
//        println(TemperatureMeasurement(Temperature.of(100.0, Kelvin),
//                TimeStamp.fromMillisecondsSinceEpoch(100000)))
//    }
//    migrateDatabase()

//    val temperatureRepository = KtormTemperatureRepository(
//            DatabaseCredentials(
//                    "jdbc:h2:mem:testdb",
//                    "org.h2.Driver",
//                    "sa",
//                    ""))
//
//    temperatureRepository.persist(
//            TemperatureMeasurement(Temperature.ABSOLUTE_ZERO, TimeStamp.now()))
//
    val grovePi = GrovePi4J()
    grovePi.use {
//        val led = GroveLedFactory(grovePi4J)
//                .createDimmableLed(GrovePiZero.D3)
//        val indicator = LedButtonIndicator(led)
//
//        val button = GroveButtonFactory(grovePi4J).
//                aButton(GrovePiZero.A0, indicator)
//        button.start()
//
//
//        val rotary = GroveRotarySensorFactory(grovePi4J)
//                .createRotarySensor(GrovePiZero.A2)
//
//        val buzzer = GroveBuzzerFactory(grovePi4J)
//                .createAdjustableBuzzerOn(GrovePiZero.D3)
//
//        rotary.addStatusChangedListener { percentageTurned -> buzzer.turnOn(percentageTurned) }

//        val temperatureHumiditySensorFactory = GroveTemperatureHumiditySensorFactory(grovePi4J)
//        val sensor = temperatureHumiditySensorFactory.createDHT11(GrovePiZero.A0)
//        sensor.start()
//
//
        val display = GroveLcd.on(GrovePiZero.I2c)
        display.setText("1234567" , "8901234567890123456789012345678")
        display.setBackground(BackgroundColor.TURQUOISE)

        Thread.sleep(10000)

        display.stop()
//        sensor.subscribe { t -> println(t)}

    }
//        val humidityIndicator = LedHighHumidityIndicator(led)
//
//        sensor.subscribe({ measurment -> if (measurment.humidity.relativeHumidity > Fraction.ofPercentage(50.0)) },
//                Period.of(10.0, Second))
//
//
//        sensor.subscribe(
//                { measurement -> humidityIndicator.toggle(measurement.humidity) },
//                Period.of(30.0, Second))
//
//
//        sensor.subscribe(
//                { measurement -> temperatureRepository.persist(measurement.toTemperatureMeasurement()) },
//                Period.of(1.0, Minute))
//    }
//
//
//
//
//    println("Hello, World")
}

fun migrateDatabase() {
    val sqlConnection = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "")
    val database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(JdbcConnection(sqlConnection))
    val liquibase = Liquibase("src/main/resources/db-changelog.xml", FileSystemResourceAccessor(), database)
    liquibase.update("main")
}

class LedButtonIndicator(private val led: DimmableLed) : ButtonStatusChangedListener {
    var percentageTurnedOn = Fraction.ofPercentage(0.0)

    override fun onStatusChanged(newStatus: ButtonStatus) {
        when (newStatus) {
            PRESSED -> {
                val newPercentage = (percentageTurnedOn.percentage + 10.0) % 100.0
                this.percentageTurnedOn = Fraction.ofPercentage(newPercentage)
                led.turnOn(percentageTurnedOn)
            }
            NOT_PRESSED -> led.turnOff()
        }
    }
}

class LedHighHumidityIndicator(private val led: Led) {
    fun toggle(newMeasurement: RelativeHumidity) {
        val percentage = newMeasurement.relativeHumidity.percentage
        when {
            percentage > 50.0 -> led.turnOn()
            else -> led.turnOff()
        }
    }
}

//<<<<<<< Updated upstream
//import nl.dutchland.grove.button.ButtonStatus
//import nl.dutchland.grove.button.GroveButtonFactory
//import nl.dutchland.grove.grovepiports.GrovePiZero
//import nl.dutchland.grove.grovepiports.zero.GrovePiZero_A0
//import nl.dutchland.grove.led.GroveLedFactory
//import java.io.FileWriter
//import java.util.concurrent.Executors
//
//fun main(args: Array<String>) {
//    val runner = Executors.newSingleThreadExecutor()
//    val monitor = Monitor()
//
//    val writer = FileWriter("test.txt")
//    writer.use {
//        writer.write("something")
//    }
//
//    val grovePi = GrovePi4J()
//
//    GrovePiZero_A0()
//
//    runner.execute {
//        grovePi.use {
//            val led = GroveLedFactory(grovePi)
//                    .createLed(GrovePiZero.A1)
//
//            val button = GroveButtonFactory(grovePi)
//                    .aButton(GrovePiZero.A0, listOf { a ->
//                        when (a) {
//                            ButtonStatus.NOT_PRESSED -> led.turnOff()
//                            ButtonStatus.PRESSED -> led.turnOn()
//                        }
//                    })
//
//            button.start()
//
//            while (monitor.isRunning) {
//
//            }
//
//            button.stop()
//        }
//    }
//=======

