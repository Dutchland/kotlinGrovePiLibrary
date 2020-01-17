package nl.dutchland.grove.rgblcd

import nl.dutchland.grove.OutputDevice
import nl.dutchland.grove.grovepiports.I2cPort
import nl.dutchland.grove.utility.Fraction
import org.iot.raspberry.grovepi.devices.GroveRgbLcd

class GroveLcd private constructor(groveRgbLcd: () -> GroveRgbLcd) : OutputDevice {
    private val MAX_COLOR_VALUE = 255.0
    private val lcd by lazy { groveRgbLcd.invoke() }

    companion object {
        fun on(port: I2cPort): GroveLcd {
            return GroveLcd { GroveRgbLcdPi4J(port.i2cNumber) }
        }
    }

    fun setBackground(color: BackgroundColor) {
        val red = (MAX_COLOR_VALUE * color.red.fraction).toInt()
        val green = (MAX_COLOR_VALUE * color.green.fraction).toInt()
        val blue = (MAX_COLOR_VALUE * color.blue.fraction).toInt()
        lcd.setRGB(red, green, blue)
    }

    fun setText(text: String) {
        lcd.setText(text)
    }

    fun setText(firstLine: String, secondLine: String) {
        lcd.setText(firstLine + "\n" + secondLine)
    }

    override fun stop() {
        setBackground(BackgroundColor.NO_BACKLIGHT)
        setText("")
        lcd.close()
    }
}

data class BackgroundColor(val red: Fraction, val green: Fraction, val blue: Fraction) {
    companion object {
        fun RED(brightness: Fraction) = BackgroundColor(brightness, Fraction.ZERO, Fraction.ZERO)
        val RED = BackgroundColor(Fraction.HUNDRED_PERCENT, Fraction.ZERO, Fraction.ZERO)

        fun GREEN(brightness: Fraction) = BackgroundColor(Fraction.ZERO, brightness, Fraction.ZERO)
        val GREEN = BackgroundColor(Fraction.ZERO, Fraction.HUNDRED_PERCENT, Fraction.ZERO)

        fun BLUE(brightness: Fraction) = BackgroundColor(Fraction.ZERO, Fraction.ZERO, brightness)
        val BLUE = BackgroundColor(Fraction.ZERO, Fraction.ZERO, Fraction.HUNDRED_PERCENT)

        fun YELLOW(brightness: Fraction) = BackgroundColor(brightness, brightness, Fraction.ZERO)
        val YELLOW = BackgroundColor(Fraction.HUNDRED_PERCENT, Fraction.HUNDRED_PERCENT, Fraction.ZERO)

        fun TURQUOISE(brightness: Fraction) = BackgroundColor(Fraction.ZERO, brightness, brightness)
        val TURQUOISE = BackgroundColor(Fraction.ZERO, Fraction.HUNDRED_PERCENT, Fraction.HUNDRED_PERCENT)

        fun PINK(brightness: Fraction) = BackgroundColor(brightness, Fraction.ZERO, brightness)
        val PINK = BackgroundColor(Fraction.HUNDRED_PERCENT, Fraction.ZERO, Fraction.HUNDRED_PERCENT)

        fun WHITE(brightness: Fraction) = BackgroundColor(brightness, brightness, brightness)
        val WHITE = BackgroundColor(Fraction.HUNDRED_PERCENT, Fraction.HUNDRED_PERCENT, Fraction.HUNDRED_PERCENT)

        val NO_BACKLIGHT = BackgroundColor(Fraction.ZERO, Fraction.ZERO, Fraction.ZERO)
    }
}