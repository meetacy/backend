package app.meetacy.backend.types.serialization

import app.meetacy.backend.types.datetime.DateTime
import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class OptionalSerializable internal constructor(private val iso8601: String) {
    fun type() = DateTime.parse(iso8601)
}

fun DateTime.serializable() = DateTimeSerializable(iso8601)