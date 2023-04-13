package app.meetacy.backend.types.meeting

import app.meetacy.backend.types.access.AccessHash

@JvmInline
value class MeetingIdentity private constructor(val string: String) {
    constructor(meetingId: MeetingId, accessHash: AccessHash) : this("${meetingId.long}:${accessHash.string}")

    val id get() = string
        .split(":")
        .first()
        .toLong()
        .let(::MeetingId)

    val accessHash get() = string
        .split(":", limit = 2)
        .last()
        .let(::AccessHash)

    companion object {
        val REGEX = Regex("\\d+:.{256}")

        fun parse(identity: String): MeetingIdentity? {
            if (!identity.matches(REGEX)) return null
            return MeetingIdentity(identity)
        }
    }
}
