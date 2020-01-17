package nl.dutchland.grove

import com.pi4j.io.i2c.I2CBus
import com.pi4j.io.i2c.I2CDevice
import com.pi4j.io.i2c.I2CFactory
import org.iot.raspberry.grovepi.GrovePiSequenceVoid
import org.iot.raspberry.grovepi.devices.GroveRgbLcd
import java.io.IOException
import java.util.logging.Level
import java.util.logging.Logger

class GroveRgbLcdPi4J : GroveRgbLcd() {
    private val bus: I2CBus
    private val rgb: I2CDevice
    private val text: I2CDevice

    override fun close() {
        try {
            bus.close()
        } catch (ex: IOException) {
            Logger.getLogger("GrovePi").log(Level.SEVERE, null, ex)
        }
    }

    @Throws(IOException::class)
    override fun execRGB(sequence: GrovePiSequenceVoid<*>) {
        synchronized(this) { sequence.execute(IO(rgb)) }
    }

    @Throws(IOException::class)
    override fun execTEXT(sequence: GrovePiSequenceVoid<*>) {
        synchronized(this) { sequence.execute(IO(text)) }
    }

    init {
        bus = I2CFactory.getInstance(I2CBus.BUS_1)
        rgb = bus.getDevice(DISPLAY_RGB_ADDR)
        text = bus.getDevice(DISPLAY_TEXT_ADDR)
        init()
    }
}