package app.meetacy.backend.types.serialization.datetime

import app.meetacy.backend.types.datetime.Date
import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class DateSerializable internal constructor(private val iso8601: String) {
    fun type() = Date.parse(iso8601)
}

fun Date.serializable() = DateSerializable(iso8601)
