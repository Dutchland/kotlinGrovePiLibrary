package nl.dutchland.grove

import com.pi4j.wiringpi.Gpio.delay
import liquibase.Liquibase
import liquibase.database.DatabaseFactory
import liquibase.database.jvm.JdbcConnection
import liquibase.pro.packaged.v
import liquibase.resource.FileSystemResourceAccessor
import me.liuwj.ktorm.database.use
import nl.dutchland.grove.button.ButtonStatus
import nl.dutchland.grove.button.ButtonStatus.*
import nl.dutchland.grove.button.ButtonStatusChangedListener
import nl.dutchland.grove.button.GroveButtonFactory
import nl.dutchland.grove.buzzer.GroveBuzzerFactory
import nl.dutchland.grove.grovepiports.GrovePiZero
import nl.dutchland.grove.led.DimmableLed
import nl.dutchland.grove.led.GroveLedFactory
import nl.dutchland.grove.led.Led
import kotlin.concurrent.fixedRateTimer
import nl.dutchland.grove.rotary.GroveRotarySensorFactory
import nl.dutchland.grove.temperatureandhumidity.GroveTemperatureHumiditySensor
import nl.dutchland.grove.temperatureandhumidity.GroveTemperatureHumiditySensorFactory
import nl.dutchland.grove.temperatureandhumidity.TemperatureMeasurement
import nl.dutchland.grove.temperaturerepository.DatabaseCredentials
import nl.dutchland.grove.temperaturerepository.KtormTemperatureRepository
import nl.dutchland.grove.utility.Fraction
import nl.dutchland.grove.utility.RelativeHumidity
import nl.dutchland.grove.utility.TimeStamp
import nl.dutchland.grove.utility.temperature.Kelvin
import nl.dutchland.grove.utility.temperature.Temperature
import nl.dutchland.grove.utility.time.Minute
import nl.dutchland.grove.utility.time.Period
import nl.dutchland.grove.utility.time.Second
import org.iot.raspberry.grovepi.GrovePi
import org.iot.raspberry.grovepi.devices.GroveRgbLcd
import java.sql.DriverManager
import kotlin.concurrent.fixedRateTimer

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
    val grovePi4J: GrovePi = GrovePi4J()
    grovePi4J.use {
        val led = GroveLedFactory(grovePi4J)
                .createDimmableLed(GrovePiZero.D3)
        val indicator = LedButtonIndicator(led)

        val button = GroveButtonFactory(grovePi4J).
                aButton(GrovePiZero.A0, indicator)
        button.start()


        val rotary = GroveRotarySensorFactory(grovePi4J)
                .createRotarySensor(GrovePiZero.A2)

        val buzzer = GroveBuzzerFactory(grovePi4J)
                .createAdjustableBuzzerOn(GrovePiZero.D3)

        rotary.addStatusChangedListener { percentageTurned -> buzzer.turnOn(percentageTurned) }

//        val temperatureHumiditySensorFactory = GroveTemperatureHumiditySensorFactory(grovePi4J)
//        val sensor = temperatureHumiditySensorFactory.createDHT11(GrovePiZero.A0)
//        sensor.start()
//
//
//        val display = GroveRgbLcdPi4J()
//        display.setText("Hallo")
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

