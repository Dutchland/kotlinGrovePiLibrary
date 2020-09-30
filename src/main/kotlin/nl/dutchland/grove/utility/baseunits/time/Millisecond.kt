package nl.dutchland.grove.utility.baseunits.time

import nl.dutchland.grove.utility.StandardUnitPrefix.Milli

typealias ms = Millisecond
object Millisecond : Time.Unit by Milli * Second


