package nl.dutchland.grove.demo.property

import nl.dutchland.grove.demo.property.PropertyHandler.Companion.CREATED_BY_PROPERTY
import nl.dutchland.grove.demo.property.PropertyHandler.Companion.DATE_FORMAT
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter.*
import java.util.*

fun main() {
    val handler = PropertyHandler()

    // De compiler kan niet helpen hier
    handler.setProperty(PropertyHandler.CREATED_BY_PROPERTY, LocalDate.now().format(PropertyHandler.DATE_FORMAT))
    handler.setProperty(CREATED_BY_PROPERTY, LocalDate.now().format(DATE_FORMAT))

    // Verkeerde dateformat
    handler.setProperty(CREATED_BY_PROPERTY, LocalDate.now().format(ISO_LOCAL_DATE))

    // Verkeerde volgorde argumenten
    handler.setProperty(LocalDate.now().format(PropertyHandler.DATE_FORMAT), PropertyHandler.CREATED_BY_PROPERTY)

    // Invoer is geen valide datum

    val someDate = "This is not a date"
    handler.setProperty(CREATED_BY_PROPERTY, someDate)


    // De gegevens: DateTimeFormatter.BASIC_ISO_DATE en "createdBy" zijn hard gekoppeld
    // --> Zorg dat deze gegevens in 1 class zijn samengebracht


    // Compiler to the rescue!!
    handler.setPropertyTypeSafe(CreatedByProperty, LocalDate.now())

//    handler.setPropertyTypeSafe(CreatedByProperty, "This is not a date")
//    handler.setPropertyTypeSafe(LocalDate.now(), CreatedByProperty)


    // V2
    val property: PropertyV2 = CreatedByPropertyV2.withValue(Date())
    val property2: PropertyV2 = CreatedByPropertyV2.withValue { getDateFromSomewhere() }
    handler.setProperty(property)
    handler.setProperty(property2)
}

fun someMethod() {
    someMethodToSetProperty("This is not a date")
}

fun someMethodToSetProperty(someProperty: String) {
    someMethodToSetProperty2(someProperty)
}

fun someMethodToSetProperty2(someDate: String) {
    handler.setProperty(CREATED_BY_PROPERTY, someDate)
}


val handler = PropertyHandler()

object CreatedByProperty : Property<LocalDate> {
    override val propertyName = "createdBy"

    override fun valueAsString(value: LocalDate): String {
        return value.format(BASIC_ISO_DATE)
    }
}

//interface LocalDateProperty : Property<LocalDate> {
//    override fun valueAsString(value: LocalDate): String {
//        return value.format(BASIC_ISO_DATE)
//    }
//}

interface Property<T> {
    val propertyName: String
    fun valueAsString(value: T): String
}

class CreatedByPropertyV2 private constructor(private val dateValue: LocalDate) : PropertyV2 {
    override val key = "createdBy"

    override val value: String
        get() = dateValue.format(BASIC_ISO_DATE)

    companion object {
        fun withValue(date: Date): CreatedByPropertyV2 {
            return CreatedByPropertyV2(Instant.ofEpochMilli(date.getTime())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate())
        }

        fun withValue(provider: () -> LocalDate): CreatedByPropertyV2 {
            return CreatedByPropertyV2(provider.invoke())
        }
    }
}

interface PropertyV2 {
    val key: String
    val value: String
}


fun getDateFromSomewhere(): LocalDate {
    return LocalDate.now()
}
