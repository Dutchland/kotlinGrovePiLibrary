package nl.dutchland.grove

import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import nl.dutchland.grove.button.ButtonStatus
import nl.dutchland.grove.button.ButtonStatus.PRESSED
import nl.dutchland.grove.events.EventBus
import nl.dutchland.grove.events.MuteEvent
import nl.dutchland.grove.events.VolumeChangedEvent
import nl.dutchland.grove.led.Led
import nl.dutchland.grove.rotary.RotaryChangedListener
import nl.dutchland.grove.utility.baseunits.length.*
import nl.dutchland.grove.utility.baseunits.mass.Mass
import nl.dutchland.grove.utility.baseunits.mass.gr
import nl.dutchland.grove.utility.baseunits.mass.kg
import nl.dutchland.grove.utility.baseunits.time.Hour
import nl.dutchland.grove.utility.baseunits.time.Period
import nl.dutchland.grove.utility.baseunits.time.Second
import nl.dutchland.grove.utility.baseunits.time.s
import nl.dutchland.grove.utility.derivedunits.area.*
import nl.dutchland.grove.utility.derivedunits.massdensity.MassDensity
import nl.dutchland.grove.utility.derivedunits.massdensity.div
import nl.dutchland.grove.utility.derivedunits.speed.Speed
import nl.dutchland.grove.utility.derivedunits.volume.*

class SpeedObservable : ObservableOnSubscribe<Double> {
    override fun subscribe(emitter: ObservableEmitter<Double>) {
        TODO("Not yet implemented")
    }
}

fun length() {
    val length1: Length = Length.of(100.0, m)
    val length2: Length = Length.of(100.0, Meter)

    val lengthTypealiasses =
            listOf(
                    Millimeter, mm,
                    Centimeter, cm,
                    Decimeter, dm,
                    Meter, m,
                    Hectometer, hm,
                    Kilometer, km
            )

    // A typealias of Length is Distance
    val length3: Distance = Distance.of(50.0, km)

    // Dividing an Area by a Length --> Length
    val area1: Area = Area.of(20.0, cm2)
    val length4: Length = area1 / Length.of(8.0, cm)



}

fun area() {
    val area1: Area = Area.of(20.0, m2)

    val areaTypealiasses =
            listOf(
                    SquaredMillimeter, mm2,
                    SquaredCentimeter, cm2,
                    SquaredDecimeter, dm2,
                    SquaredMeter, m2,
                    SquaredHectometer, hm2,
                    SquaredKilometer, km2
            )

    // Multiplying to Lengths --> Area
    val area2: Area = Length.of(2.0, Meter) * Length.of(10.0, Meter)
}


fun main() {
    val massDensityUnit = kg/m3

    val massDensityOfGold = MassDensity.of(19.3, gr/cm3)
    val massDensityOfGold1 = Mass.of(38.6, gr) / Volume.of(2.0, cm3)
    val massDensityOfGold2 = Mass.of(19.3, gr) / cm3

    // 1kg goud. Hoeveel cm3?
    val volumeOfClumbOfGold : Volume = Mass.of(1.0, kg) / massDensityOfGold

    // 20cm3 of gold. How much mass?
    val massOfClumpOfGold : Mass = massDensityOfGold * Volume.of(20.0, cm3)
    val massOfClumpOfGold1 : Mass =  Volume.of(20.0, cm3) * massDensityOfGold


    val boxLength = Length.of(1.0, Meter)
    val boxWidth = Length.of(50.0, Centimeter)
    val boxHeight = Length.of(8.0, Decimeter)

    val boxGroundArea = boxLength * boxWidth
    val boxVolume = boxLength * boxWidth * boxHeight

    println("The box ground area is: ${boxGroundArea.valueIn(m2)} ${m2.shortName}")
    println("The boxvolume is: ${boxVolume.valueIn(m3)} ${m3.shortName}")
    println("The boxvolume is: ${boxVolume.valueIn(Liter)} ${Liter.longName}")
    println("The boxvolume is: ${boxVolume.valueIn(dm3)} ${dm3.shortName}")

    val speed2 = Length.of(100.0, Kilometer) / Hour
    speed2.valueIn(Speed.Unit(Millimeter, Second))
    speed2.valueIn(Millimeter / Second)

    val acceleration = speed2 / Second
    val meterPerSecondSquared = (m / s) / s

    val cmsquared = cm * cm


    val speed1 = Speed.of(100.0, Kilometer / Hour)


    val speed3 = Length.of(100.0, Kilometer) / Period.of(5.0, Hour)

    val speed = Speed.of(10.0, Meter).per(Second)
    val length = speed * Period.of(1.0, Hour)
//    println("I traveled a distance of ${length.valueIn(Kilometer)} ${Kilometer.shortName}")


    val distanceToTheMoon = Length.of(384_400.0, Kilometer)
    val speedOfCar = Speed.of(130.0, Kilometer).per(Hour)
    val travelTime = distanceToTheMoon / speedOfCar

    println("By car it would take me ${travelTime.valueIn(Hour)} $Hour to get to the moon")

//
//    val numberOfBoxedThatFitInACubicMeter = Volume.of(1.0, m3) / boxVolume
//
//    println(boxVolume.valueIn(liter))

//    val mass1 = Mass.of(5.0, Kilogram)
//    val mass2 = Mass.of(500.0, Gram)
//
//    val area1 = Area.of(10.0, Area.Unit.ofSquared(Meter))
//    val area2 = Area.of(10.0, m2)
//
//    val density = MassDensity.of(10.0, Kilogram).perCubic(Meter)
//
//
//    val speed2 = Speed.of(1.0, Meter).per(Second)
//    val speed3 = Speed.of(1.0, MeterPerSecond)
//    val speed4 = Speed.of(1.0, Speed.Unit(Meter, Second))
//
//    val energyAmount = EnergyAmount.of(100.0, KiloCalorie)
//    println("In ${Joule} this would be: ${energyAmount.valueIn(Joule)}")
//
//
//    val area = Area.of(10.0, km2)
//
//    val length1 = Length.of(3.0, Meter)
//
//    listOf(length1, length1 + Length.of(10.0, Inch))
//            .sorted()


//    val aKiloGram = Mass.of(1.0, Kilogram)
//    val aStone = Mass.of(1.0, Stone)
//    println("1 ${Kilogram} is ${aKiloGram.valueIn(Stone)} ${Stone}")
//    println("1 ${Stone} is ${aStone.valueIn(Kilogram)} ${Kilogram}")
//
////    val speed = Speed.of(20.0, Kilometer, Minute)
////    val speed = Speed.of(20.0, Meter, Second)
//
//    val weigthInStone :Mass = Mass.of(2.0, Stone)
//    val weightInKilogram = weigthInStone.valueIn(Kilogram)


//
//
//    val sumOfWeights = aKiloGram + aStone
//
//    val sum = listOf(aKiloGram, aStone)
//            .sum()

//    ​Observable<Todo> todoObservable = Observable.create(new ObservableOnSubscribe<Todo>() {
//        ​@Override
//        ​public void subscribe(ObservableEmitter<Todo> emitter) throws Exception {
//        ​try {
//        ​List<Todo> todos = RxJavaUnitTest.this.getTodos();
//        ​for (Todo todo : todos) {
//            ​emitter.onNext(todo);
//            ​}
//        ​emitter.onComplete();
//        ​} catch (Exception e) {
//        ​emitter.onError(e);
//        ​}
//        ​}
//
//
//    val obs = Observable.create()
//
//
//        val lengthOnSubscribe = ObservableOnSubscribe<Length> { e -> e.onNext(Length.of(1.0, Meter)) }
//    val observable = Observable.create(lengthOnSubscribe)
//
//    observable.subscribe { s -> println(s) }

//    val lentghObservable = observable.com
//    lentghObservable.subscribe { s -> println(s) }


//    println("arstarstt")
//    val groveCo2Sensor : Co2Sensor = GroveCo2Sensor(GrovePiBoard.SERIAL(Serial.DEFAULT_COM_PORT)) { println(it) }
//    groveCo2Sensor.start()

//    eventBus.subscribe<VolumeChangedEvent> { volumeChangeEvent ->
//        speaker.setVolume(volumeChangeEvent.volumePercentage)
//    }
//
//    eventBus.subscribe<MuteEvent> {
//        speaker.handleMuteEvent(it)
//        muteIndicator.handleMuteEvent(it)
//    }
//
//    inputDevices.forEach { device ->
//        device.start()
//    }
}


fun Speaker.handleMuteEvent(muteEvent: MuteEvent) {
    if (muteEvent.muteIsOn) this.mute()
    else this.unMute()
}

//private val grovePi: GrovePi = GrovePi4J()

fun Led.handleMuteEvent(muteEvent: MuteEvent) {
    if (muteEvent.muteIsOn) this.turnOn()
    else this.turnOff()
}


private val eventBus = EventBus()
private val volumeChangedListener: RotaryChangedListener = { volumePercentage ->
    eventBus.post(VolumeChangedEvent(volumePercentage))
}

//private val volumeRotary: RotarySensor = GroveRotarySensorFactory(grovePi)
//        .on(GrovePiBoard.A0, volumeChangedListener)
//
//private val muteButton: Button = GroveButtonBuilder(grovePi)
//        .on(GrovePiBoard.A1)
//        .withListener { eventBus.post(it.toMuteEvent()) }
//        .build()
//
//private val speaker: Speaker = Speaker(GroveBuzzerFactory(grovePi)
//        .adjustableBuzzerOn(GrovePiBoard.D3))
//
//private val muteIndicator: Led = GroveLedFactory(grovePi)
//        .on(GrovePiBoard.D2)

//private val inputDevices = listOf(
//        volumeRotary,
//        muteButton
//)


fun ButtonStatus.toMuteEvent(): MuteEvent {
    return when (this) {
        PRESSED -> MuteEvent(true)
        else -> MuteEvent(false)
    }
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

