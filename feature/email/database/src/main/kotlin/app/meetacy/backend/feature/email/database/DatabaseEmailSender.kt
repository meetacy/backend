package app.meetacy.backend.feature.email.database

object DatabaseEmailSender {
    fun sendEmail(
        email: String,
        text: String,
        onEmailReceived: (String, String) -> Unit = { _, _ -> }
    ) {
        println("""
            NEW MESSAGE SENT TO $email:
            Message content:
        """.trimIndent())

        println(text)

        onEmailReceived(email, text)
    }
}