package app.meetacy.backend.types.serialization.invitation

import app.meetacy.backend.types.invitation.InvitationIdentity
import kotlinx.serialization.Serializable

@JvmInline
@Serializable
value class InvitationIdentitySerializable(val string: String) {
    init {
        type()
    }

    fun type() = InvitationIdentity.parse(string)
}

fun InvitationIdentity.serializable() = InvitationIdentitySerializable(string)
