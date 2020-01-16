package nl.dutchland.grove

import com.pi4j.io.i2c.I2CBus
import com.pi4j.io.i2c.I2CDevice
import com.pi4j.io.i2c.I2CFactory
import com.pi4j.io.i2c.I2CFactory.UnsupportedBusNumberException
import org.iot.raspberry.grovepi.GrovePi
import org.iot.raspberry.grovepi.GrovePiSequence
import org.iot.raspberry.grovepi.GrovePiSequenceVoid
import org.iot.raspberry.grovepi.devices.GroveRgbLcd

/**
 * Create a new GrovePi interface using the Pi4j library
 *
 * @author Eduardo Moranchel <emoranchel></emoranchel>@asmatron.org>
 */
class GrovePi4J : GrovePi {
    private val bus: I2CBus
    private val device: I2CDevice
    override fun getLCD(): GroveRgbLcd {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    //  @Override
    override fun <T> exec(sequence: GrovePiSequence<T>): T {
        synchronized(this) { return sequence.execute(IO(device)) }
    }

    //  @Override
    override fun execVoid(sequence: GrovePiSequenceVoid<*>) {
        synchronized(this) { sequence.execute(IO(device)) }
    }

    //  @Override
    override fun close() {
        bus.close()
    }


    companion object {
        private const val GROVEPI_ADDRESS = 4
    }

    init {
        bus = I2CFactory.getInstance(I2CBus.BUS_1)
        device = bus.getDevice(GROVEPI_ADDRESS)
    }
}