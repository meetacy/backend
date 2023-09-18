package app.meetacy.backend.types.serializable.invitation

import app.meetacy.backend.types.serializable.meetings.Meeting
import app.meetacy.backend.types.serializable.users.User
import kotlinx.serialization.Serializable

@Serializable
data class Invitation(
    val identity: InvitationId,
    val invitedUser: User,
    val inviterUser: User,
    val meeting: Meeting,
    val isAccepted: Boolean?
)
