package app.meetacy.backend.types.datetime

import app.meetacy.backend.types.annotation.UnsafeConstructor
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*
import java.time.Instant as JavaInstant
import java.time.LocalDate as JavaLocalDate
import java.util.Date as JavaDate

@JvmInline
value class Date @UnsafeConstructor constructor(val iso8601: String) {

    val atStartOfDay: DateTime get() = javaLocalDate
        .atStartOfDay()
        .atOffset(ZoneOffset.UTC)
        .toInstant()
        .meetacyDateTime

    val javaLocalDate: JavaLocalDate get() = JavaLocalDate.parse(iso8601)

    @OptIn(UnsafeConstructor::class)
    companion object {
        fun today(): Date = JavaDate().meetacyDate
        fun parse(iso8601: String): Date = parseOrNull(iso8601)
            ?: error("Given string '$iso8601' is not in iso8601 format for date without time")
        fun parseOrNull(iso8601: String): Date? = try {
            LocalDate.parse(iso8601)
            Date(iso8601)
        } catch (_: Throwable) {
            null
        }
    }
}

val JavaDate.meetacyDate: Date get() = toInstant().meetacyDate

@OptIn(UnsafeConstructor::class)
val JavaInstant.meetacyDate: Date get() = Date(DateTimeFormatter.ISO_DATE.format(this))

@OptIn(UnsafeConstructor::class)
val JavaLocalDate.meetacyDate: Date get() = Date("$this")
