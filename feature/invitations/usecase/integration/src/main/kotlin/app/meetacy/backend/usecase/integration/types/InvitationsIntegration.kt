package app.meetacy.backend.usecase.integration.types

import app.meetacy.backend.types.serializable.invitation.Invitation
import app.meetacy.backend.types.serializable.invitation.serializable
import app.meetacy.backend.types.serializable.meetings.type
import app.meetacy.backend.usecase.types.InvitationView

fun InvitationView.toEndpoint(): Invitation {
    return Invitation(
        id.serializable(), invitedUser.mapToEndpoint(),
        inviterUser.mapToEndpoint(), meeting.type(), isAccepted
    )
}
