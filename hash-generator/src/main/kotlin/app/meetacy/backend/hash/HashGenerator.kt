package app.meetacy.backend.hash

private fun getRandomString(@Suppress("SameParameterValue") length: Int) : String {
    val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
    return (1..length)
        .map { allowedChars.random() }
        .joinToString("")
}

object HashGenerator {
    fun generate(): String {
        val time = "${System.currentTimeMillis()}"
        return getRandomString(length = 256 - time.length) + time
    }
}
