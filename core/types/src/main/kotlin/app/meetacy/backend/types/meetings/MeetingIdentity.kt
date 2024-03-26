package app.meetacy.backend.types.meetings

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
        val REGEX = Regex("\\d+:.{${AccessHash.LENGTH}}")

        fun parseOrNull(identity: String): MeetingIdentity? {
            if (!identity.matches(REGEX)) return null
            return MeetingIdentity(identity)
        }

        fun parse(identity: String) = parseOrNull(identity) ?: error("Cannot parse identity")
    }
}
