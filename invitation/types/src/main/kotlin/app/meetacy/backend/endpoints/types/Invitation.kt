package app.meetacy.backend.endpoints.types

import app.meetacy.backend.endpoint.types.meeting.Meeting
import app.meetacy.backend.endpoint.types.user.User
import app.meetacy.backend.types.serialization.invitation.InvitationIdSerializable
import kotlinx.serialization.Serializable

@Serializable
data class Invitation(
    val identity: InvitationIdSerializable,
    val invitedUser: User,
    val inviterUser: User,
    val meeting: Meeting,
    val isAccepted: Boolean?
)
