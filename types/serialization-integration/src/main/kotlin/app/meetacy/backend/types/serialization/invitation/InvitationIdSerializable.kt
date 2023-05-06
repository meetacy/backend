package app.meetacy.backend.types.serialization.invitation

import app.meetacy.backend.types.invitation.InvitationId
import kotlinx.serialization.Serializable

@JvmInline
@Serializable
value class InvitationIdSerializable(val string: String) {
    init {
        type()
    }

    fun type() = InvitationId.parse(string)
}

fun InvitationId.serializable() = InvitationIdSerializable(long.toString())