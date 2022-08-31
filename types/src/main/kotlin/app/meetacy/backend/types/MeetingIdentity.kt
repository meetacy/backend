package app.meetacy.backend.types

// a combination of meeting id and access hash
@JvmInline
value class MeetingIdentity private constructor(val identity: String) {
    constructor(meetingId: MeetingId, accessHash: AccessHash) : this("$meetingId:$accessHash")

    val meetingId get() = identity
        .split(":")
        .first()
        .toLong()
        .let(::MeetingId)

    val accessHash get() = identity
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
