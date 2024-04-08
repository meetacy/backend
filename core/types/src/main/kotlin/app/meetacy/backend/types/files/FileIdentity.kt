package app.meetacy.backend.types.files

import app.meetacy.backend.types.access.AccessHash

@JvmInline
value class FileIdentity private constructor(val string: String) {
    constructor(fileId: FileId, accessHash: AccessHash) : this("${fileId.long}:${accessHash.string}")

    val id get() = string
        .split(":")
        .first()
        .toLong()
        .let(::FileId)

    val accessHash get() = string
        .split(":", limit = 2)
        .last()
        .let(::AccessHash)

    companion object {
        val REGEX = Regex("\\d+:.{${AccessHash.LENGTH}}")

        fun parse(identity: String): FileIdentity? {
            if (!identity.matches(REGEX)) return null
            return FileIdentity(identity)
        }
    }
}
