package nl.dutchland.grove

import nl.dutchland.grove.button.ButtonStatus
import nl.dutchland.grove.button.GroveButtonFactory
import nl.dutchland.grove.grovepiports.zero.GrovePiZero
import nl.dutchland.grove.grovepiports.zero.GrovePiZero_A0
import nl.dutchland.grove.led.GroveLedFactory
import java.io.FileWriter
import java.util.concurrent.Executors

fun main(args: Array<String>) {
    val runner = Executors.newSingleThreadExecutor()
    val monitor = Monitor()

    val writer = FileWriter("test.txt")
    writer.use {
        writer.write("something")
    }

    val grovePi = GrovePi4J()

    GrovePiZero_A0()

    runner.execute {
        grovePi.use {
            val led = GroveLedFactory(grovePi)
                    .createLed(GrovePiZero.A1)

            val button = GroveButtonFactory(grovePi)
                    .aButton(GrovePiZero.A0, listOf { a ->
                        when (a) {
                            ButtonStatus.NOT_PRESSED -> led.turnOff()
                            ButtonStatus.PRESSED -> led.turnOn()
                        }
                    })

            button.start()

            while (monitor.isRunning) {

            }

            button.stop()
        }
    }
}

