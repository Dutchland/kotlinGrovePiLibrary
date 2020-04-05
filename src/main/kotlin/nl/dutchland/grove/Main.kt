package nl.dutchland.grove

import com.pi4j.wiringpi.Gpio.delay
import nl.dutchland.grove.button.ButtonStatus
import nl.dutchland.grove.button.ButtonStatus.*
import nl.dutchland.grove.button.ButtonStatusChangedListener
import nl.dutchland.grove.button.GroveButtonFactory
import nl.dutchland.grove.led.GroveLedFactory
import nl.dutchland.grove.led.Led
import nl.dutchland.grove.rgblcd.GroveLcd
import nl.dutchland.grove.temperatureandhumidity.GroveTemperatureHumiditySensorFactory

import org.iot.raspberry.grovepi.GrovePi

fun main() {
    val grovePi: GrovePi = GrovePi4J()
//    val led = GroveLedFactory(grovePi).createLed(nl.dutchland.grove.grovepiports.GrovePi.A0)
//
//    val indicator = LedIndicator(led)
//
//    val button = GroveButtonFactory(grovePi).aButton(nl.dutchland.grove.grovepiports.GrovePi.A1, indicator)
//    button.start()

    val temperatureSensorDht11 = GroveTemperatureHumiditySensorFactory(grovePi)
            .createDHT11(nl.dutchland.grove.grovepiports.GrovePi.A0)

    val temperatureSensorDht22 = GroveTemperatureHumiditySensorFactory(grovePi)
            .createDHT22(nl.dutchland.grove.grovepiports.GrovePi.A1)

    temperatureSensorDht11.subscribe { th -> println(th) }
    temperatureSensorDht22.subscribe { th -> println(th) }
    temperatureSensorDht11.start()
    temperatureSensorDht22.start()

    val display = GroveLcd.on(nl.dutchland.grove.grovepiports.GrovePi.I2c3)
    val tempHumidityDisplay = TempHumidityDisplay(display, temperatureSensorDht11)
    tempHumidityDisplay.start()


    Runtime.getRuntime().addShutdownHook(object : Thread() {
        override fun run() {
            println("Shutting down")
//            button.stop()
//            led.stop()
            temperatureSensorDht11.stop()
            temperatureSensorDht22.stop()
            tempHumidityDisplay.stop()
            display.stop()

            delay(100)
            grovePi.close();
        }
    })
}

//class LightDisplay(private val display: GroveLcd, private val lightSensor: LightSensor) {
//    private var newestValue: Double = 0.0
//    private lateinit var timer: Timer
//
//    init {
//        lightSensor.subscribe { s -> onLightChanged(s) }
//    }
//
//    private fun onLightChanged(newMeasurement: LightSensorMeasurement) {
//        newestValue = newMeasurement.value.percentage
//    }
//
//    fun start() {
//        timer = fixedRateTimer("Polling sensor timer", false, 0, 10000)
//        { display.setText("Light: $newestValue%") }
//    }
//
//    fun stop() {
//        timer.cancel()
//    }
//}

//    val lightSensor = GroveLightSensorFactory(grovePi).createLigthSensorV1_2(GrovePiZero.A0)
//    lightSensor.start()
//    lightSensor.subscribe { n -> println(n) }
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
class LedIndicator(private val led: Led) : ButtonStatusChangedListener {
    override fun onStatusChanged(newStatus: ButtonStatus) {
        when (newStatus) {
            PRESSED -> led.turnOn()
            NOT_PRESSED -> led.turnOff()
        }
    }
}

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

