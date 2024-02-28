package app.meetacy.backend.types.access

import app.meetacy.backend.types.users.UserId

// a combination of user id and access token
@JvmInline
value class AccessIdentity private constructor(val string: String) {
    constructor(userId: UserId, accessToken: AccessToken) : this("${userId.long}:${accessToken.string}")

    val userId get() = string
        .split(":")
        .first()
        .toLong()
        .let(::UserId)

    val accessToken get() = string
        .split(":", limit = 2)
        .last()
        .let(::AccessToken)

    companion object {
        val REGEX = Regex("\\d+:.{256}")

        fun parseOrNull(identity: String): AccessIdentity? {
            if (!identity.matches(REGEX)) return null
            return AccessIdentity(identity)
        }

        fun parse(identity: String) = parseOrNull(identity) ?: error("Cannot parse authorization token")
    }
}
