package app.meetacy.backend.types.serializable.datetime

import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class DateOrTime(val string: String)
