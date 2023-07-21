package app.meetacy.backend.types.invitation

@JvmInline
value class InvitationId(val long: Long) {
    companion object {
        fun parse(string: String): InvitationId = InvitationId(string.toLong())
    }
}
