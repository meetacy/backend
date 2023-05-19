package app.meetacy.backend.endpoint.types

import app.meetacy.backend.types.serialization.datetime.DateTimeSerializable
import app.meetacy.backend.types.serialization.invitation.InvitationIdentitySerializable
import kotlinx.serialization.Serializable

@Serializable
data class Invitation(
    val identity: InvitationIdentitySerializable,
    val expiryDate: DateTimeSerializable,
    val invitedUser: User,
    val invitorUser: User,
    val meeting: Meeting,
    val isAccepted: Boolean?
)
