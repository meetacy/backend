package app.meetacy.backend.types

// a combination of user id and access token
@JvmInline
value class AccessIdentity private constructor(val string: String) {
    constructor(userIdentity: UserId, accessToken: AccessToken) : this("${userIdentity.long}:${accessToken.string}")

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

        fun parse(identity: String): AccessIdentity? {
            if (!identity.matches(REGEX)) return null
            return AccessIdentity(identity)
        }
    }
}
