package nl.dutchland.grove

import nl.dutchland.grove.button.ButtonStatus
import nl.dutchland.grove.button.ButtonStatus.*
import nl.dutchland.grove.button.ButtonStatusChangedListener
import nl.dutchland.grove.button.GroveButtonFactory
import nl.dutchland.grove.grovepiports.zero.GrovePiZero
import nl.dutchland.grove.led.GroveLedFactory
import nl.dutchland.grove.led.Led
import nl.dutchland.grove.temperatureandhumidity.GroveTemperatureHumiditySensorFactory
import nl.dutchland.grove.utility.RelativeHumidity
import nl.dutchland.grove.utility.time.Minute
import nl.dutchland.grove.utility.time.Period
import org.iot.raspberry.grovepi.GrovePi

fun main() {
    val grovePi4J: GrovePi = GrovePi4J()

    val ledFactory = GroveLedFactory(grovePi4J)
    val led = ledFactory.createLed(GrovePiZero.A1)

    val indicator = LedButtonIndicator(led)
    val buttonFactory = GroveButtonFactory(grovePi4J)
    val button = buttonFactory.aButton(GrovePiZero.A0, indicator)


    val temperatureHumiditySensorFactory = GroveTemperatureHumiditySensorFactory(grovePi4J)
    val sensor = temperatureHumiditySensorFactory.createDHT11(GrovePiZero.A2)
    val humidityIndicator = LedHighHumidityIndicator(led)


    sensor.subscribe(
            { measurement -> humidityIndicator.toggle(measurement.humidity) },
            Period.of(1.0, Minute))

    println("Hello, World")
}

class LedButtonIndicator(private val led: Led) : ButtonStatusChangedListener {
    fun toggle(isPressed: ButtonStatus) {
        when (isPressed) {
            PRESSED -> led.turnOn()
            NOT_PRESSED -> led.turnOff()
        }
    }

    override fun invoke(newButtonStatus: ButtonStatus) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
//import nl.dutchland.grove.grovepiports.zero.GrovePiZero
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

