package app.meetacy.backend.hash

import app.meetacy.backend.types.HASH_LENGTH

private fun getRandomString(@Suppress("SameParameterValue") length: Int): String {
    val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
    return (1..length)
        .map { allowedChars.random() }
        .joinToString("")
}

object HashGenerator {
    fun generate(): String {
        return getRandomString(HASH_LENGTH)
    }
}
