package nl.dutchland.grove.utility.nonsiunits

import nl.dutchland.grove.utility.derivedunits.energy.EnergyAmount

class ElectronVolt : EnergyAmount.Unit by EnergyAmount.Unit.ofFactorized(
        toJouleFactor = 1.602176634 * 10e-19,
        shortName = "eV",
        longName = "ElectronVolt")