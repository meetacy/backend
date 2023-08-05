package app.meetacy.backend.endpoint.types

import app.meetacy.backend.endpoint.types.meeting.Meeting
import app.meetacy.backend.endpoint.types.user.User
import app.meetacy.backend.types.serializable.invitation.InvitationId
import kotlinx.serialization.Serializable

@Serializable
data class Invitation(
    val identity: InvitationId,
    val invitedUser: User,
    val inviterUser: User,
    val meeting: Meeting,
    val isAccepted: Boolean?
)
