package app.meetacy.backend.endpoint.types

import app.meetacy.backend.types.serialization.datetime.DateTimeSerializable
import app.meetacy.backend.types.serialization.meeting.MeetingIdSerializable
import app.meetacy.backend.types.serialization.user.UserIdSerializable
import kotlinx.serialization.Serializable

@Serializable
data class Invitation(
    val id: String,
    val expiryDate: DateTimeSerializable,
    val invitedUserId: UserIdSerializable,
    val invitorUserId: UserIdSerializable,
    val meeting: MeetingIdSerializable
)
