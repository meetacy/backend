package app.meetacy.backend.integration.mock.emailLink

import app.meetacy.backend.mock.email.MockEmailText

object MockTextProviderIntegration : MailerTextProvider {
    override fun createConfirmMessage(email: String, confirmHash: String): String =
        MockEmailText.getText(email, confirmHash)
}
