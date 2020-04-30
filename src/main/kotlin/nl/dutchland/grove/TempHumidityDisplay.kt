package nl.dutchland.grove

import nl.dutchland.grove.rgblcd.BackgroundColor
import nl.dutchland.grove.rgblcd.GroveLcd
import nl.dutchland.grove.temperatureandhumidity.TemperatureHumidityMeasurement
import nl.dutchland.grove.temperatureandhumidity.TemperatureHumiditySensor
import nl.dutchland.grove.utility.temperature.Celsius
import java.math.BigDecimal
import java.util.*
import kotlin.concurrent.fixedRateTimer

class TempHumidityDisplay(private val display: GroveLcd, private val tempHumiditySensor: TemperatureHumiditySensor) : OutputDevice {
    private var newestValue: TemperatureHumidityMeasurement
    private var timer: Timer? = null

    init {
        tempHumiditySensor.subscribe { s -> onLightChanged(s) }
        newestValue = tempHumiditySensor.getTemperatureHumidity()
    }

    private fun onLightChanged(newMeasurement: TemperatureHumidityMeasurement) {
        newestValue = newMeasurement
    }

    fun start() {
        timer = fixedRateTimer("Polling sensor timer", false, 0, 10000)
        {
            val roundedTemperature = BigDecimal.valueOf(newestValue.temperature.valueIn(Celsius))
                    .setScale(0)
            val roundedHumidity = BigDecimal.valueOf(newestValue.humidity.relativeHumidity.percentage)
                    .setScale(0)
            display.setText("Temp: $roundedTemperature C \n Hum: $roundedHumidity %")

            if (roundedHumidity > BigDecimal.valueOf(60) ||
                    roundedHumidity < BigDecimal.valueOf(40)) {
                display.setBackground(BackgroundColor.YELLOW)
            } else
                display.setBackground(BackgroundColor.NO_BACKLIGHT)
        }
    }

    override fun stop() {
        display.stop()
        timer?.cancel()
    }
}