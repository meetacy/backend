package app.meetacy.backend.types.invitation

import app.meetacy.backend.types.access.AccessHash

@JvmInline
value class InvitationIdentity private constructor(val string: String) {
    constructor(invitationId: InvitationId, accessHash: AccessHash) : this("${invitationId.long}:${accessHash.string}")

    val id get() = string
        .split(":")
        .first()
        .toLong()
        .let(::InvitationId)

    val accessHash get() = string
        .split(":")
        .last()
        .let(::AccessHash)

    companion object {
        val REGEX = Regex("\\d+:.{256}")

        fun parse(identity: String): InvitationIdentity? {
            if (!identity.matches(REGEX)) return null
            return InvitationIdentity(identity)
        }
    }
}