package app.meetacy.backend.types.serializable.meetings

import kotlinx.serialization.Serializable

@JvmInline
@Serializable
value class MeetingIdentity(val string: String)