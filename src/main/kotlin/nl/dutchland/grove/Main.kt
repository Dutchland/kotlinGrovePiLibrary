package nl.dutchland.grove

import com.pi4j.wiringpi.Gpio.delay
import hep.dataforge.meta.buildMeta
import io.ktor.util.KtorExperimentalAPI
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.NonCancellable.isActive
//import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import liquibase.Liquibase
import liquibase.database.DatabaseFactory
import liquibase.database.jvm.JdbcConnection
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
import nl.dutchland.grove.lightsensor.GroveLightSensorFactory
import nl.dutchland.grove.temperatureandhumidity.GroveTemperatureHumiditySensorFactory

import nl.dutchland.grove.utility.Fraction
import nl.dutchland.grove.utility.RelativeHumidity
import org.iot.raspberry.grovepi.GrovePi
import scientifik.plotly.Plotly
import scientifik.plotly.makeFile
import scientifik.plotly.makeHtml
import scientifik.plotly.models.Trace
import scientifik.plotly.server.serve
import java.lang.Math.*
import java.sql.DriverManager


fun main(vararg args: String) {
//    migrateDatabase()
    val grovePi : GrovePi = GrovePi4J()

    val lightSensor = GroveLightSensorFactory(grovePi).createLigthSensorV1_2(GrovePiZero.A0)
    lightSensor.start()
    lightSensor.subscribe{ n -> println(n) }

//    val led = GroveLedFactory(grovePi)
//            .createLed(GrovePiZero.D3)
//
//    val indicator = LedButtonIndicator(led)
//
//    val button = GroveButtonFactory(grovePi).aButton(GrovePiZero.A0, indicator)
//    button.start()
//
//    val lightSensor : LightSensor = GroveLightSensorFactory(grovePi)
//            .createLigthSensorV1_2(GrovePiZero.A2)
//
//
    val temperatureSensor = GroveTemperatureHumiditySensorFactory(grovePi)
            .createDHT11(GrovePiZero.A1)

    temperatureSensor.subscribe { th -> println(th) }
    temperatureSensor.start()
//
//    val display = GroveLcd.on(GrovePiZero.I2c)
//    display.setText("Maayke", "is lief!")
//    display.setBackground(BackgroundColor.TURQUOISE(Fraction.ofPercentage(10.0)))
//    val rotary = GroveRotarySensorFactory(grovePi)
//            .createRotarySensor(GrovePiZero.A2)
//
//    val buzzer = GroveBuzzerFactory(grovePi)
//            .createBuzzerOn(GrovePiZero.A1)

    Runtime.getRuntime().addShutdownHook(object : Thread() {
        override fun run() {
            println("Shutting down")
            lightSensor.stop()
            temperatureSensor.stop()
//            button.stop()
//            temperatureSensor.stop()
////            rotary.stop()
//            delay(100)
//
//            led.stop()
////            display.stop()
////            buzzer.stop()
//
            delay(100)
            grovePi.close();
        }
    })




}
//    }


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

//
//        Thread.sleep(10000)
//        display.stop()

//        display.stop()
//        sensor.subscribe { t -> println(t)}

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

//fun migrateDatabase() {
//    val sqlConnection = DriverManager.getConnection("jdbc:postgresql://192.168.86.25:5432/postgres", "postgres", "mysecretpassword")
//    val database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(JdbcConnection(sqlConnection))
//    val liquibase = Liquibase("src/main/resources/db-changelog.xml", FileSystemResourceAccessor(), database)
//    liquibase.update("main")
//}
//
//class LedButtonIndicator(private val led: Led) : ButtonStatusChangedListener {
//    override fun onStatusChanged(newStatus: ButtonStatus) {
//        when (newStatus) {
//            PRESSED -> led.turnOn()
//            NOT_PRESSED -> led.turnOff()
//        }
//    }
//}
//
//class DimmableLedIndicator(private val led: DimmableLed) : ButtonStatusChangedListener {
//    var percentageTurnedOn = Fraction.ofPercentage(0.0)
//
//    override fun onStatusChanged(newStatus: ButtonStatus) {
//        when (newStatus) {
//            PRESSED -> {
//                val newPercentage = (percentageTurnedOn.percentage + 10.0) % 100.0
//                this.percentageTurnedOn = Fraction.ofPercentage(newPercentage)
//                led.turnOn(percentageTurnedOn)
//            }
//            NOT_PRESSED -> led.turnOff()
//        }
//    }
//}
//
//class LedHighHumidityIndicator(private val led: Led) {
//    fun toggle(newMeasurement: RelativeHumidity) {
//        val percentage = newMeasurement.relativeHumidity.percentage
//        when {
//            percentage > 50.0 -> led.turnOn()
//            else -> led.turnOff()
//        }
//    }
//}

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

