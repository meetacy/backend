package app.meetacy.backend.types.serializable.meetings

import kotlinx.serialization.Serializable

@JvmInline
@Serializable
value class MeetingId(val string: String)