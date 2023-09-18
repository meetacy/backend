package app.meetacy.backend.types.serializable.meetings

import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class MeetingId(val long: Long)
