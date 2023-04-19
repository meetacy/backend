package app.meetacy.backend.types.user

@JvmInline
value class Username(val string: String) {

    companion object {
        fun parse(string: String): Username {
            require(checkUsername(string)) { "Invalid username" }
            return Username(string)
        }
        fun parseOrNull(string: String): Username? {
            if (!checkUsername(string)) return null
            return Username(string)
        }
    }
}

val String.username: Username get() = Username.parse(string = this)

fun checkUsername(username: String): Boolean {
    val regex = Regex("^[a-zA-Z][a-zA-Z0-9_]*$")
    return regex.matches(username)
}
