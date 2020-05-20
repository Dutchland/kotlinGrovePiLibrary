package nl.dutchland.grove.demo.property

import java.time.format.DateTimeFormatter

class PropertyHandler {
    fun setProperty(property: String, value: String) {
        // Do something
    }

    /**
     * Geef alleen waardes mee die .toString() overriden
     */
    fun setProperty(property: String, value: Any) {
        // Do something
    }

    fun <T> setPropertyTypeSafe(property: Property<T>, value: T) {
        // Do something
    }


    fun setProperty(property: PropertyV2) {
        property.value
        property.key
    }

    companion object { // Dit is niet de manier om constants te definieren in Kotlin.
        val DATE_FORMAT = DateTimeFormatter.BASIC_ISO_DATE
        val CREATED_BY_PROPERTY = "createdBy"
    }
}