package app.meetacy.backend.types.datetime

import app.meetacy.backend.types.annotation.UnsafeConstructor
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeParseException
import java.util.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days
import java.time.Instant as JavaInstant
import java.time.LocalDate as JavaLocalDate
import java.util.Date as JavaDate

private val iso8601DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)

@JvmInline
value class Date @UnsafeConstructor constructor(val iso8601: String) {
    operator fun compareTo(today: Date): Int = this.javaLocalDate.compareTo(today.javaLocalDate)

    val atStartOfDay: DateTime get() = iso8601DateFormat.parse(iso8601).meetacyDateTime
    val javaLocalDate: JavaLocalDate get() = JavaLocalDate.parse(iso8601)
    val epochDays: Long get() = javaLocalDate.toEpochDay()

    operator fun minus(duration: Duration): Date {
        return javaLocalDate.minusDays(duration.inWholeDays).meetacyDate
    }

    operator fun plus(duration: Duration): Date {
        return javaLocalDate.plusDays(duration.inWholeDays).meetacyDate
    }

    @OptIn(UnsafeConstructor::class)
    companion object {
        fun today(): Date = JavaDate().meetacyDate

        fun yesterday() = today().minus(1.days)
        fun tomorrow() = today().plus(1.days)
        fun ofEpochDays(days: Long): Date = LocalDate.ofEpochDay(days).meetacyDate
        fun parse(iso8601: String): Date = parseOrNull(iso8601)
            ?: error("Given string '$iso8601' is not in iso8601 format for date without time")
        fun parseOrNull(iso8601: String): Date? = try {
            JavaLocalDate.parse(iso8601)
            Date(iso8601)
        } catch (_: DateTimeParseException) {
            null
        }
    }
}

@OptIn(UnsafeConstructor::class)
val JavaDate.meetacyDate: Date get() = Date(iso8601DateFormat.format(this))

val JavaInstant.meetacyDate: Date get() = JavaDate(toEpochMilli()).meetacyDate

@OptIn(UnsafeConstructor::class)
val JavaLocalDate.meetacyDate: Date get() = Date("$this")
