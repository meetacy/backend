package app.meetacy.backend.types.serializable.invitation

import app.meetacy.backend.types.invitation.InvitationId
import app.meetacy.backend.types.serializable.serialization
import app.meetacy.backend.types.serializable.invitation.InvitationId as InvitationIdSerializable

fun InvitationIdSerializable.type() = serialization { InvitationId.parse(string) }
fun InvitationId.serializable() = InvitationIdSerializable(long.toString())
