package app.meetacy.backend.types.datetime

import java.time.LocalDate
import java.util.Date as JavaDate
import app.meetacy.backend.types.datetime.Date as DateType
import app.meetacy.backend.types.datetime.DateTime as DateTimeType
import java.time.Instant as JavaInstant
import java.time.LocalDate as JavaLocalDate

sealed interface DateOrTime {
    val date: DateType
    val javaLocalDate: JavaLocalDate
    val dateTime: DateTimeType?
    val iso8601: String

    class Date(override val date: DateType) : DateOrTime {
        override val dateTime: Nothing? get() = null
        override val iso8601: String get() = date.iso8601
        val atStartOfDay: DateTime get() = DateTime(date.atStartOfDay)
        override val javaLocalDate: JavaLocalDate get() = date.javaLocalDate

        companion object {
            fun today(): Date = Date(DateType.today())
            fun parse(iso8601: String): Date =
                Date(DateType.parse(iso8601))
            fun parseOrNull(iso8601: String): Date? =
                DateType.parseOrNull(iso8601)?.let(DateOrTime::Date)
        }
    }

    class DateTime(override val dateTime: DateTimeType) : DateOrTime {
        override val date: DateType get() = dateTime.date
        override val iso8601: String get() = dateTime.iso8601
        override val javaLocalDate: JavaLocalDate get() = dateTime.date.javaLocalDate
        val javaDate: JavaDate get() = dateTime.javaDate
        val javaInstant: JavaInstant get() = dateTime.javaInstant

        companion object {
            fun now(): DateTime = DateTime(DateTimeType.now())
            fun parse(iso8601: String): DateTime =
                DateTime(DateTimeType.parse(iso8601))
            fun parseOrNull(iso8601: String): DateTime? =
                DateTimeType.parseOrNull(iso8601)?.let(DateOrTime::DateTime)
        }
    }

    companion object {
        fun parse(iso8601: String): DateOrTime =
            parseOrNull(iso8601) ?: error("Given string '$iso8601' is not in iso8601 format")

        fun parseOrNull(iso8601: String): DateOrTime? =
            DateTimeType.parseOrNull(iso8601)?.let(DateOrTime::DateTime) ?:
            DateType.parseOrNull(iso8601)?.let(DateOrTime::Date)
    }
}
