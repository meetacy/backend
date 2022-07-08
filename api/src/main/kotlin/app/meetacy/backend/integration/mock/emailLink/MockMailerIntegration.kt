package app.meetacy.backend.integration.mock.emailLink

import app.meetacy.backend.endpoint.auth.email.link.Mailer
import app.meetacy.backend.mock.email.MockEmailSender

interface MailerTextProvider {
    fun createConfirmMessage(email: String, confirmHash: String): String
}

class MockMailerIntegration(
    private val onEmailReceived: (String, String) -> Unit,
    private val textProvider: MailerTextProvider
) : Mailer {
    override fun sendConfirmEmail(email: String, confirmHash: String) =
        MockEmailSender.sendEmail(
            email = email,
            text = textProvider.createConfirmMessage(email, confirmHash),
            onEmailReceived
        )
}
