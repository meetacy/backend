package app.meetacy.backend.types.serializable.invitation

import app.meetacy.backend.types.invitation.InvitationView
import app.meetacy.backend.types.serializable.meetings.serializable
import app.meetacy.backend.types.serializable.users.serializable

fun InvitationView.serializable(): Invitation {
    return Invitation(
        id.serializable(), invitedUser.serializable(),
        inviterUser.serializable(), meeting.serializable(), isAccepted
    )
}
