package app.meetacy.backend.usecase.integration.types

import app.meetacy.backend.endpoint.types.Invitation
import app.meetacy.backend.types.serialization.datetime.serializable
import app.meetacy.backend.types.serialization.invitation.serializable
import app.meetacy.backend.usecase.types.InvitationView

fun InvitationView.toEndpoint(): Invitation {
    return Invitation(
        identity.serializable(), expiryDate.serializable(), invitedUserView.mapToEndpoint(),
        invitorUserView.mapToEndpoint(), meetingView.mapToEndpoint(), isAccepted
    )
}