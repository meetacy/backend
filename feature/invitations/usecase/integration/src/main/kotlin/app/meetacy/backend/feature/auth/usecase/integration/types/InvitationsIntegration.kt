package app.meetacy.backend.feature.auth.usecase.integration.types

import app.meetacy.backend.endpoint.types.Invitation
import app.meetacy.backend.types.serializable.invitation.serializable
import app.meetacy.backend.usecase.types.InvitationView

fun InvitationView.toEndpoint(): Invitation {
    return Invitation(
        id.serializable(), invitedUser.mapToEndpoint(),
        inviterUser.mapToEndpoint(), meeting.mapToEndpoint(), isAccepted
    )
}
