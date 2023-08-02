package app.meetacy.backend.types.serializable.meeting

import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class MeetingId(val long: Long)
