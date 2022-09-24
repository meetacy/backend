package app.meetacy.backend.types

@JvmInline
value class FileIdentity private constructor(val string: String) {
    constructor(fileId: FileId, accessToken: AccessToken) : this("${fileId.long}:${accessToken.string}")

    val fileId get() = string
        .split(":")
        .first()
        .toLong()
        .let(::FileId)

    val accessToken get() = string
        .split(":", limit = 2)
        .last()
        .let(::AccessToken)

    companion object {
        val REGEX = Regex("\\d+:.{256}")

        fun parse(identity: String): FileIdentity? {
            if (!identity.matches(REGEX)) return null
            return FileIdentity(identity)
        }
    }
}
