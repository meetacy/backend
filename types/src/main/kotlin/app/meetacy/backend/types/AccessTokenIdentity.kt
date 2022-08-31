package app.meetacy.backend.types

// a combination of user id and access token
@JvmInline
value class AccessTokenIdentity private constructor(val identity: String) {
    constructor(userIdentity: UserIdentity, accessToken: AccessToken) : this("$userIdentity:$accessToken")

    val userId get() = identity
        .split(":")
        .first()
        .toLong()
        .let(::UserId)

    val accessToken get() = identity
        .split(":", limit = 2)
        .last()
        .let(::AccessToken)

    companion object {
        val REGEX = Regex("\\d+:.{256}")

        fun parse(identity: String): AccessTokenIdentity? {
            if (!identity.matches(REGEX)) return null
            return AccessTokenIdentity(identity)
        }
    }
}
