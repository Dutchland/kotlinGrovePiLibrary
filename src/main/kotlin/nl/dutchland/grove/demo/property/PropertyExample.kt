package nl.dutchland.grove.demo.property

import nl.dutchland.grove.demo.property.PropertyHandler.Companion.CREATED_BY_PROPERTY
import nl.dutchland.grove.demo.property.PropertyHandler.Companion.DATE_FORMAT
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.*
import java.util.*

fun main() {
    val handler = PropertyHandler()

    // De compiler kan niet helpen hier
//    handler.setProperty(PropertyHandler.CREATED_BY_PROPERTY, LocalDate.now().format(PropertyHandler.DATE_FORMAT))
    handler.setProperty(CREATED_BY_PROPERTY, LocalDate.now().format(DATE_FORMAT))

    // Verkeerde dateformat
    handler.setProperty(CREATED_BY_PROPERTY, LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE))

    // Verkeerde volgorde argumenten
    handler.setProperty(LocalDate.now().format(PropertyHandler.DATE_FORMAT), PropertyHandler.CREATED_BY_PROPERTY)

    // Invoer is geen valide datum
    val someDate = "This is not a date"
    handler.setProperty(CREATED_BY_PROPERTY, someDate)


    // De gegevens: DateTimeFormatter.BASIC_ISO_DATE en "createdBy" zijn hard gekoppeld
    // --> Koppel ze dan ook!


    // Compiler to the rescue!!
    handler.setPropertyTypeSafe(CreatedByProperty, LocalDate.now())
//    handler.setPropertyTypeSafe(CreatedByProperty, "This is not a date")
//    handler.setPropertyTypeSafe(LocalDate.now(), CreatedByProperty)

    //Voordelen:
    // - TypeSafe
    // - Aanroepers hoeven niks te weten van conversies (dus dat kunnen ze ook niet fout doen, geen verkeerd ISO formaat gebruiken)
    // - PropertyHandler is closed for modification. Extension --> Extra property class toevoegen
    // - Minder regels code (niet elke aanroep hoeft zelf de conversie te doen)


    // Combineer key en value.
    // Veel 'constructors' veel flexibiliteit voor de gebruiker
    val property: PropertyV2 = CreatedByPropertyV2.withValue(Date())
    val property1: PropertyV2 = CreatedByPropertyV2.withValue(LocalDate.now())
    val property2: PropertyV2 = CreatedByPropertyV2.withValue { getDateFromSomewhere() }
    val property3: PropertyV2 = CreatedByPropertyV2.withValue(RabbitTimeStamp())
}

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

        fun withValue(date: LocalDate): CreatedByPropertyV2 {
            return CreatedByPropertyV2(date)
        }

        fun withValue(timeStamp: RabbitTimeStamp): CreatedByPropertyV2 {
            return withValue(timeStamp.toDate())
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

//interface PropertyV2 {
//    fun getKey(): String
//    fun getValue(): String
//}


fun getDateFromSomewhere(): LocalDate {
    return LocalDate.now()
}

class RabbitTimeStamp {
    fun toDate(): Date {
        return Date()
    }
}

