package nl.dutchland.grove.events

import nl.dutchland.grove.utility.Fraction

inline class VolumeChangedEvent(val volumePercentage: Fraction) : Event