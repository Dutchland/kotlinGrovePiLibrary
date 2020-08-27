package nl.dutchland.grove.co2

internal class GroveCo2AirConcentration(private val data: ByteArray) : AirConcentration {
    override val partsPerMillion: Int
        get() {
            val highLevel = data[2]
            val lowLevel = data[3]
            return highLevel * 265 + lowLevel
        }
}