package app.meetacy.usecase.integration.types

import app.meetacy.backend.endpoints.types.Invitation
import app.meetacy.backend.types.serialization.invitation.serializable
import app.meetacy.backend.usecase.integration.types.mapToEndpoint
import app.meetacy.backend.usecase.types.InvitationView

fun InvitationView.toEndpoint(): Invitation {
    return Invitation(
        id.serializable(), invitedUser.mapToEndpoint(),
        inviterUser.mapToEndpoint(), meeting.mapToEndpoint(), isAccepted
    )
}
