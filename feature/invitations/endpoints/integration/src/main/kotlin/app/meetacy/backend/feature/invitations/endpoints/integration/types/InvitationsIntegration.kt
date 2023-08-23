package app.meetacy.backend.feature.invitations.endpoints.integration.types

import app.meetacy.backend.feature.invitations.usecase.types.InvitationView
import app.meetacy.backend.feature.users.usecase.integration.types.mapToEndpoint
import app.meetacy.backend.types.serializable.invitation.Invitation
import app.meetacy.backend.types.serializable.invitation.serializable
import app.meetacy.backend.types.serializable.meetings.serializable

fun InvitationView.toEndpoint(): Invitation {
    return Invitation(
        id.serializable(), invitedUser.mapToEndpoint(),
        inviterUser.mapToEndpoint(), meeting.serializable(), isAccepted
    )
}
