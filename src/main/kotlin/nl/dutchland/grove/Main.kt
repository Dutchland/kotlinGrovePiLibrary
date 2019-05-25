package nl.dutchland.grove

import nl.dutchland.grove.utility.temperature.Celcius
import nl.dutchland.grove.utility.temperature.Temperature

fun main(args: Array<String>) {
    val temp1 = Temperature.of(20.0, Celcius)
    val temp2 = Temperature.of(21.0, Celcius)

    if (temp1 > temp2) {

    }

    println("Hello, World")
}

