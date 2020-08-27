package nl.dutchland.grove.co2

import nl.dutchland.grove.InputDevice

typealias Co2Listener = (Co2Measurement) -> Unit

interface Co2Sensor : InputDevice