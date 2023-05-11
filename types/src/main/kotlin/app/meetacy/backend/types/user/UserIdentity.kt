package app.meetacy.backend.types.user

import app.meetacy.backend.types.access.AccessHash

// a combination of user id and access hash
@JvmInline
value class UserIdentity private constructor(val string: String) {
    constructor(userId: UserId, accessHash: AccessHash) : this("${userId.long}:${accessHash.string}")

    val id get() = string
        .split(":")
        .first()
        .toLong()
        .let(::UserId)

    val accessHash get() = string
        .split(":", limit = 2)
        .last()
        .let(::AccessHash)

    companion object {
        val REGEX = Regex("\\d+:.{256}")

        fun parse(identity: String): UserIdentity? {
            if (!identity.matches(REGEX)) return null
            return UserIdentity(identity)
        }
    }
}
