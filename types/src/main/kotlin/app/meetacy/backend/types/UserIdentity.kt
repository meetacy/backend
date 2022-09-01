package app.meetacy.backend.types

// a combination of user id and access hash
@JvmInline
value class UserIdentity private constructor(val string: String) {
    constructor(userId: UserId, accessHash: AccessHash) : this("${userId.long}:${accessHash.string}")

    val userId get() = string
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
