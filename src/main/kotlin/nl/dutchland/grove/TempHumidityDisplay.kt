package nl.dutchland.grove

import nl.dutchland.grove.events.EventBus
import nl.dutchland.grove.events.TemperatureEvent
import nl.dutchland.grove.events.TemperatureHumidityEvent
import nl.dutchland.grove.rgblcd.BackgroundColor
import nl.dutchland.grove.rgblcd.GroveLcd
import nl.dutchland.grove.temperatureandhumidity.TemperatureHumidityMeasurement
import nl.dutchland.grove.temperatureandhumidity.TemperatureHumiditySensor
import nl.dutchland.grove.utility.temperature.Celsius
import java.math.BigDecimal
import java.util.*
import kotlin.concurrent.fixedRateTimer

class TempHumidityDisplay(private val display: GroveLcd, eventBus: EventBus) {
    private var newestValue: TemperatureHumidityMeasurement? = null
    private var timer: Timer? = null

    init {
        eventBus.subscribe<TemperatureHumidityEvent> { newestValue = it.measurement }
    }

    private fun onLightChanged(newMeasurement: TemperatureHumidityMeasurement) {
        val roundedTemperature = BigDecimal.valueOf(newMeasurement.temperature.valueIn(Celsius))
                .setScale(0)
        val roundedHumidity = BigDecimal.valueOf(newMeasurement.humidity.relativeHumidity.percentage)
                .setScale(0)
        display.setText("Temp: $roundedTemperature C \n Hum: $roundedHumidity %")

        if (roundedHumidity > BigDecimal.valueOf(60) ||
                roundedHumidity < BigDecimal.valueOf(40)) {
            display.setBackground(BackgroundColor.YELLOW)
        } else
            display.setBackground(BackgroundColor.NO_BACKLIGHT)
    }
}