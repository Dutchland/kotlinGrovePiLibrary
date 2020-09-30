package nl.dutchland.grove.utility.baseunits.electriccurrent

typealias A = Ampere
object Ampere : ElectricCurrent.Unit by ElectricCurrent.Unit.ofParameterized(
        "Ampere",
        "A",
        1.0)