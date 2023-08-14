package app.meetacy.backend.feature.auth.usecase.integration.types

import app.meetacy.backend.endpoint.types.Invitation
import app.meetacy.backend.feature.auth.usecase.types.InvitationView
import app.meetacy.backend.types.serializable.invitation.serializable

fun InvitationView.toEndpoint(): Invitation {
    return Invitation(
        id.serializable(), invitedUser.mapToEndpoint(),
        inviterUser.mapToEndpoint(), meeting.mapToEndpoint(), isAccepted
    )
}
