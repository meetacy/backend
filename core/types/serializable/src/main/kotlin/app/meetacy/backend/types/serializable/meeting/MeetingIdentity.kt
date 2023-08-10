package app.meetacy.backend.types.serializable.meeting

import kotlinx.serialization.Serializable

@JvmInline
@Serializable
value class MeetingIdentity(val string: String)