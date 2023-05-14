package app.meetacy.backend.types.datetime

import app.meetacy.backend.types.annotation.UnsafeConstructor
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.time.Instant as JavaInstant
import java.util.Date as JavaDate

private val iso8601DateTimeFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").apply {
    timeZone = TimeZone.getTimeZone("UTC")
}

@JvmInline
value class DateTime @UnsafeConstructor constructor(val iso8601: String) {
    operator fun compareTo(now: DateTime): Int =
        this.epochMillis.compareTo(now.epochMillis)

    val date: Date get() = javaDate.meetacyDate

    val javaInstant: JavaInstant get() = javaDate.toInstant()

    val javaDate: JavaDate get() = iso8601DateTimeFormat.parse(iso8601)

    val epochMillis: Long get() = javaInstant.toEpochMilli()

    @OptIn(UnsafeConstructor::class)
    companion object {
        const val LENGTH = 24

        fun now(): DateTime = JavaDate().meetacyDateTime
        fun ofEpochMillis(millis: Long): DateTime = JavaDate(millis).meetacyDateTime
        fun parse(iso8601: String): DateTime = parseOrNull(iso8601)
            ?: error("Given string '$iso8601' is not in iso8601 format for date-time")
        fun parseOrNull(iso8601: String): DateTime? = try {
            iso8601DateTimeFormat.parse(iso8601)
            DateTime(iso8601)
        } catch (_: ParseException) {
            null
        }
    }
}

@OptIn(UnsafeConstructor::class)
val JavaDate.meetacyDateTime: DateTime get() = DateTime(iso8601DateTimeFormat.format(this))

val JavaInstant.meetacyDateTime: DateTime get() = JavaDate(toEpochMilli()).meetacyDateTime
