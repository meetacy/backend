package app.meetacy.backend.types.serialization

import app.meetacy.backend.types.Date
import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class DateSerializable(private val iso8601: String) {
    fun type() = Date(iso8601)
}

fun Date.serializable() = DateSerializable(iso8601)
