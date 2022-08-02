package app.meetacy.backend.hash

private fun getRandomString(@Suppress("SameParameterValue") length: Int) : String {
    val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
    return (1..length)
        .map { allowedChars.random() }
        .joinToString("")
}

object HashGenerator {
    fun generate() = getRandomString(length = 256) + System.currentTimeMillis()
}
