package nl.dutchland.grove.utility.derivedunits.energy

class KiloWattHour : EnergyAmount.Unit by EnergyAmount.Unit.ofFactorized(
        1000.0,
        "kJ",
        "KiloJoule")