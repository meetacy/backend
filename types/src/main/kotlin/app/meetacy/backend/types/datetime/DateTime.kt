package app.meetacy.backend.types.datetime

import app.meetacy.backend.types.annotation.UnsafeConstructor
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*
import java.time.Instant as JavaInstant
import java.util.Date as JavaDate

private val dateTimeRegex = Regex("""\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}}Z""")

@JvmInline
value class DateTime @UnsafeConstructor constructor(val iso8601: String) {
    val date: Date get() = javaDate.meetacyDate

    val javaInstant: JavaInstant get() = JavaInstant.parse(iso8601)

    val javaDate: JavaDate get() = JavaDate(javaInstant.toEpochMilli())

    @OptIn(UnsafeConstructor::class)
    companion object {
        fun now(): DateTime = JavaDate().meetacyDateTime
        fun parse(iso8601: String): DateTime = parseOrNull(iso8601)
            ?: error("Given string '$iso8601' is not in iso8601 format for date-time")
        fun parseOrNull(iso8601: String): DateTime? = try {
            JavaInstant.parse(iso8601)
            dateTimeRegex.matches(iso8601) || throw IllegalStateException()
            DateTime(iso8601)
        } catch (_: Throwable) {
            null
        }
    }
}

val JavaDate.meetacyDateTime: DateTime get() = toInstant().meetacyDateTime

@OptIn(UnsafeConstructor::class)
val JavaInstant.meetacyDateTime: DateTime get() = DateTime("$this")
