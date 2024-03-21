package app.meetacy.backend.types.users

import app.meetacy.backend.types.annotation.UnsafeConstructor
import app.meetacy.backend.types.annotation.UnsafeRawUsername

@OptIn(UnsafeRawUsername::class)
class Username @UnsafeConstructor constructor(
    @property:UnsafeRawUsername
    val withoutAt: String
) {
    @UnsafeRawUsername
    val at: String get() = "@$withoutAt"

    override fun hashCode(): Int {
        return withoutAt.lowercase().hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return other is Username && other.withoutAt.lowercase() == withoutAt.lowercase()
    }

    override fun toString(): String {
        return "Username(string=$withoutAt)"
    }

    @OptIn(UnsafeConstructor::class)
    companion object {
        fun parse(string: String): Username {
            require(checkUsername(string)) { "Username doesn't match the following pattern: [a-zA-Z][a-zA-Z0-9_]*" }
            return Username(string)
        }
        fun parseOrNull(string: String): Username? {
            if (!checkUsername(string)) return null
            return Username(string)
        }
    }
}

val String.username: Username get() = Username.parse(string = this)
val String.usernameOrNull: Username? get() = Username.parseOrNull(string = this)

private fun checkUsername(username: String): Boolean {
    val regex = Regex("[a-zA-Z][a-zA-Z0-9_]{4,31}")
    return regex.matches(username)
}
