package app.meetacy.backend.mock.email

object MockEmailSender {
    fun sendEmail(
        email: String,
        text: String,
        onEmailReceived: (String, String) -> Unit
    ) {
        println("""
            NEW MESSAGE SENT TO $email:
            Message content:
        """.trimIndent())

        println(text)

        onEmailReceived(email, text)
    }
}
