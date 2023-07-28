package app.meetacy.backend.types.serializable.datetime

import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class Date(val iso8601: String)
