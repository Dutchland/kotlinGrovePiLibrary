package nl.dutchland.grove

import com.pi4j.io.i2c.I2CDevice
import org.iot.raspberry.grovepi.GroveIO

class IO(private val device: I2CDevice) : GroveIO {

    override fun write(vararg command: Int) {
        val buffer = ByteArray(command.size)
        for (i in 0 until command.size) {
            buffer[i] = command[i].toByte()
        }
        device.write(buffer, 0, command.size)
    }


    override fun read(): Int {
        val read = device.read()

        return read
    }


    override fun read(buffer: ByteArray): ByteArray {
        device.read(buffer, 0, buffer.size)
        return buffer
    }

}