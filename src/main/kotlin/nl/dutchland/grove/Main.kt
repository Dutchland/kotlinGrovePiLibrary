package nl.dutchland.grove

import nl.dutchland.grove.button.Button
import nl.dutchland.grove.button.ButtonStatus
import nl.dutchland.grove.button.ButtonStatus.PRESSED
import nl.dutchland.grove.button.GroveButtonFactory
import nl.dutchland.grove.buzzer.GroveBuzzerFactory
import nl.dutchland.grove.events.EventBus
import nl.dutchland.grove.events.MuteEvent
import nl.dutchland.grove.events.VolumeChangedEvent
import nl.dutchland.grove.led.GroveLedFactory
import nl.dutchland.grove.led.Led
import nl.dutchland.grove.rotary.GroveRotarySensorFactory
import nl.dutchland.grove.rotary.RotaryChangedListener
import nl.dutchland.grove.rotary.RotarySensor
import nl.dutchland.grove.utility.demo.Address
import nl.dutchland.grove.utility.demo.DutchPostcode
import nl.dutchland.grove.utility.demo.Housenumber
import nl.dutchland.grove.utility.demo.SimpleAddress
import nl.dutchland.grove.utility.length.Inch
import nl.dutchland.grove.utility.length.Length
import nl.dutchland.grove.utility.length.Meter
import nl.dutchland.grove.utility.temperature.Celsius
import nl.dutchland.grove.utility.temperature.Fahrenheit
import nl.dutchland.grove.utility.temperature.Kelvin
import nl.dutchland.grove.utility.temperature.Temperature
import org.iot.raspberry.grovepi.GrovePi
import nl.dutchland.grove.grovepiports.GrovePi as GrovePiBoard

fun main() {
    eventBus.subscribe<VolumeChangedEvent> { volumeChangeEvent ->
        speaker.setVolume(volumeChangeEvent.volumePercentage)
    }

    eventBus.subscribe<MuteEvent> {
        speaker.handleMuteEvent(it)
        muteIndicator.handleMuteEvent(it)
    }

    inputDevices.forEach { device ->
        device.start()
    }
}

fun Speaker.handleMuteEvent(muteEvent: MuteEvent) {
    if (muteEvent.muteIsOn) this.mute()
    else this.unMute()
}

fun Led.handleMuteEvent(muteEvent: MuteEvent) {
    if (muteEvent.muteIsOn) this.turnOn()
    else this.turnOff()
}

private val grovePi: GrovePi = GrovePi4J()

private val eventBus = EventBus()
private val volumeChangedListener: RotaryChangedListener = { volumePercentage ->
    eventBus.post(VolumeChangedEvent(volumePercentage))
}

private val volumeRotary: RotarySensor = GroveRotarySensorFactory(grovePi)
        .on(GrovePiBoard.A0, volumeChangedListener)

private val muteButton: Button = GroveButtonFactory(grovePi)
        .on(GrovePiBoard.A1, { eventBus.post(it.toMuteEvent()) })

private val speaker: Speaker = Speaker(GroveBuzzerFactory(grovePi)
        .adjustableBuzzerOn(GrovePiBoard.D3))

private val muteIndicator: Led = GroveLedFactory(grovePi)
        .on(GrovePiBoard.D2)

private val inputDevices: Collection<InputDevice> = listOf(volumeRotary, muteButton)

private fun temperature0(sensor: TemperatureSensor) {
    // Temperature in what scale?
    val roomTemperature: Double = sensor.currentTemperature()
    println("Temperature in Kelvin, Celsius, Fahrenheit or some other scale?????")

    persistTemperature(
            TemperatureUtil.kelvinToFahrenheit(roomTemperature))
}

fun ButtonStatus.toMuteEvent(): MuteEvent {
    return when (this) {
        PRESSED -> MuteEvent(true)
        else -> MuteEvent(false)
    }
}


private fun storeTemperature(temperature: Temperature) {
    println("Temperature of ${temperature.valueIn(Celsius)} ${Celsius}")
}

class TemperatureSensor() {
    /**
     * Return temperature in Kelvin
     */
    fun currentTemperature(): Double {
        return 4.0
    }

    fun currentTemperatureInCelsius(): Double { // Extreme naming
        return 4.0
    }

    fun currentRoomTemperature(): Temperature {
        return Temperature.of(4.0, Celsius)
    }
}

/**
 * Temperature in Kelvin
 */
fun persistTemperature(temperature: Double) {
    // Persisting
}

fun persistTemperature2(temperatureInKelvin: Double) {
    // Persisting
}

fun persistTemperatureInKelvin(temperature: Double) { // Extreme naming
    // Persisting
}

fun persistTemperature(temperature: Temperature) {
    // Persisting
}

private fun temperature() {
    // Mensen maken fouten, maak het moeilijk om fouten te maken
    val roomTemperature: Temperature = Temperature.of(22.0, Celsius)
    val temperatureInKelvin: Double = roomTemperature.valueIn(Kelvin)

    println("The current room temperature is $temperatureInKelvin $Kelvin")
    println("In $Fahrenheit this would be ${roomTemperature.valueIn(Fahrenheit)}")
}

private fun temperature(preferredTemperatureScale: Temperature.Scale, sensor: TemperatureSensor) {
    val roomTemperature: Temperature = sensor.currentRoomTemperature()
    println("The current room temperature is " +
            "${roomTemperature.valueIn(preferredTemperatureScale)} $preferredTemperatureScale")

    persistTemperature(roomTemperature)
}

private fun getCurrentRoomTemperatureFromSensor(): Temperature {
    return Temperature.of(22.0, Celsius)
}

private fun length() {
    val beamLength: Length = Length.of(1.0, Meter)
    val otherBeamLength: Length = Length.of(10.0, Inch)

    val totalLength: Length = beamLength + otherBeamLength

    println("The total length is ${totalLength.valueIn(Inch)} $Inch")
}

private fun length2() {
    val beamA: Length = Length.of(0.1, Meter)
    val beamB: Length = Length.of(10.0, Inch)

    when {
        beamA > beamB -> println("Beam A is longer then beam B")
        beamB >= beamA -> println("Beam B is longer or equal to beam A")
    }
}

private fun address() {
    val simpleAddress = SimpleAddress(
            city = "2624VV",
            postcode = "Delft",
            street = "Herculesweg",
            houseNumber = 123,
            houseNumberAddition = "A"
    )

    // Utility vs value objects
    val address = Address(
            "Delft",
            DutchPostcode("2624VV"),
            "Herculesweg",
            Housenumber(123, "A")
    )

    // Als alles een string is dan kan de compiler je niet helpen
//    val address2 = Address(
//            "2624VV",
//            "Delft",
//            "Herculesweg",
//            "123A"
//    )
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
//class LedIndicator(private val led: Led) : ButtonStatusChangedListener {
//    override fun onStatusChanged(newStatus: ButtonStatus) {
//        when (newStatus) {
//            PRESSED -> led.turnOn()
//            NOT_PRESSED -> led.turnOff()
//        }
//    }
//}


////    val indicator = LedIndicator(led)
////
////    val button = GroveButtonFactory(grovePi).aButton(nl.dutchland.grove.grovepiports.GrovePi.A1, indicator)
////    button.start()
//
//val temperatureSensorDht11 = GroveTemperatureHumiditySensorFactory(grovePi)
//        .createDHT11(nl.dutchland.grove.grovepiports.GrovePi.A0)
//
//val temperatureSensorDht22 = GroveTemperatureHumiditySensorFactory(grovePi)
//        .createDHT22(nl.dutchland.grove.grovepiports.GrovePi.A1)
//
//temperatureSensorDht11.subscribe { th -> println(th) }
//temperatureSensorDht22.subscribe { th -> println(th) }
//temperatureSensorDht11.start()
//temperatureSensorDht22.start()
//
//val display = GroveLcd.on(nl.dutchland.grove.grovepiports.GrovePi.I2c3)
//val tempHumidityDisplay = TempHumidityDisplay(display, temperatureSensorDht11)
//tempHumidityDisplay.start()
//
//
//Runtime.getRuntime().addShutdownHook(object : Thread() {
//    override fun run() {
//        println("Shutting down")
////            button.stop()
////            led.stop()
//        temperatureSensorDht11.stop()
//        temperatureSensorDht22.stop()
//        tempHumidityDisplay.stop()
//        display.stop()
//
//        delay(100)
//        grovePi.close();
//    }
//})


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

class TemperatureUtil {
    companion object {
        fun kelvinToFahrenheit(value: Double): Double {
            return 1.0
        }
    }
}