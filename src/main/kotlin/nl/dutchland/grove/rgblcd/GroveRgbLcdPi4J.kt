package nl.dutchland.grove.rgblcd

import com.pi4j.io.i2c.I2CBus
import com.pi4j.io.i2c.I2CDevice
import com.pi4j.io.i2c.I2CFactory
import nl.dutchland.grove.IO
import org.iot.raspberry.grovepi.GrovePiSequenceVoid
import org.iot.raspberry.grovepi.devices.GroveRgbLcd
import java.io.IOException
import java.lang.RuntimeException

internal class GroveRgbLcdPi4J(i2cNumber: Int) : GroveRgbLcd() {
    private val bus: I2CBus = I2CFactory.getInstance(i2cNumber)
    private val rgb: I2CDevice = bus.getDevice(DISPLAY_RGB_ADDR)
    private val text: I2CDevice = bus.getDevice(DISPLAY_TEXT_ADDR)

    init {
        init()
    }

    override fun close() {
        try {
            bus.close()
        } catch (ex: IOException) {
            throw RuntimeException(ex)
        }
    }

    override fun execRGB(sequence: GrovePiSequenceVoid<*>) {
        synchronized(this) { sequence.execute(IO(rgb)) }
    }

    override fun execTEXT(sequence: GrovePiSequenceVoid<*>) {
        synchronized(this) { sequence.execute(IO(text)) }
    }
}