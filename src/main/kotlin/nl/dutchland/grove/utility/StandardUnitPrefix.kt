package nl.dutchland.grove.utility

enum class StandardUnitPrefix(override val factor: Double,
                              override val longName: String,
                              override val symbol: String) : UnitPrefix {
    Pico(1e-12, "Pico", "p"),
    Nano(1e-9, "Nano", "n"),
    Micro(1e-6, "Micro", "µ"),
    Milli(1e-3, "Milli", "m"),
    Centi(1e-2, "Centi", "c"),
    Deci(1e-1, "Deci", "d"),
    Hecto(1e0, "Hecto", "h"),
    Kilo(1e3, "Kilo", "k"),
    Mega(1e6, "Mega", "M"),
    Giga(1e9, "Giga", "G"),
    Tera(1e12, "Tera", "T"),
}

interface UnitPrefix {
    val factor: Double
    val longName: String
    val symbol: String
}