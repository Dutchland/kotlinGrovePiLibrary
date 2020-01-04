package nl.dutchland.grove

import org.iot.raspberry.grovepi.devices.GroveRgbLcd
import org.iot.raspberry.grovepi.GrovePiSequenceVoid
import org.iot.raspberry.grovepi.GrovePiSequence
import org.iot.raspberry.grovepi.GrovePi
import java.io.Closeable


class GrovePi4J : Closeable, GrovePi {
    override fun getLCD(): GroveRgbLcd {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun <T : Any?> exec(p0: GrovePiSequence<T>?): T {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun execVoid(p0: GrovePiSequenceVoid<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    //    private val bus: I2CBus
//    private val device: I2CDevice
//
//    init {
//        this.bus = I2CFactory.getInstance(I2CBus.BUS_1)
//        this.device = bus.getDevice(GROVEPI_ADDRESS)
//    }
//
//    @Throws(IOException::class)
//    override fun <T> exec(sequence: GrovePiSequence<T>): T {
//        synchronized(this) {
//            return sequence.execute(IO(device))
//        }
//    }
//
//    @Throws(IOException::class)
//    override fun execVoid(sequence: GrovePiSequenceVoid<*>) {
//        synchronized(this) {
//            sequence.execute(IO(device))
//        }
//    }
//
    override fun close() {
//        try {
//            bus.close()
//        } catch (ex: IOException) {
//            Logger.getLogger("GrovePi").log(Level.SEVERE, null, ex)
//        }

    }
//
//    @Throws(IOException::class)
//    override fun getLCD(): GroveRgbLcd {
//        return GroveRgbLcdPi4J()
//    }
//
//    companion object {
//
//        private val GROVEPI_ADDRESS = 4
//    }
}