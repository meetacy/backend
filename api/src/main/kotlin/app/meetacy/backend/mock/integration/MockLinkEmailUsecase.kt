package app.meetacy.backend.mock.integration

import app.meetacy.backend.mock.email.MockEmailSender
import app.meetacy.backend.mock.email.MockEmailText
import app.meetacy.backend.mock.generator.MockHashGenerator
import app.meetacy.backend.mock.storage.ConfirmationStorage
import app.meetacy.backend.mock.storage.TokensStorage
import app.meetacy.backend.mock.storage.UsersStorage
import app.meetacy.backend.usecase.email.LinkEmailUsecase

private object MockLinkEmailStorage : LinkEmailUsecase.Storage {
    override suspend fun isEmailOccupied(email: String): Boolean =
        UsersStorage.isEmailOccupied(email)

    override suspend fun getUserId(token: String): Long? =
        TokensStorage.getToken(token)?.ownerId

    override suspend fun udateEmail(userId: Long, email: String) {
        UsersStorage.updateEmail(userId, email)
    }

    override suspend fun addConfirmationHash(userId: Long, email: String, confirmationHash: String) {
        ConfirmationStorage.addHash(userId, email, confirmationHash)
    }
}

private object MockLinkEmailMailer : LinkEmailUsecase.Mailer {
    override fun sendEmailOccupiedMessage(email: String) =
        MockEmailSender.sendEmail(email, MockEmailText.getOccupiedText())

    override fun sendConfirmationMessage(email: String, confirmationHash: String) =
        MockEmailSender.sendEmail(email, MockEmailText.getConfirmationText(email, confirmationHash))
}

private object MockLinkEmailHashGenerator : LinkEmailUsecase.HashGenerator {
    override fun generate() = MockHashGenerator.generate()
}

fun mockLinkEmailUsecase(): LinkEmailUsecase = LinkEmailUsecase(
    storage = MockLinkEmailStorage,
    mailer = MockLinkEmailMailer,
    hashGenerator = MockLinkEmailHashGenerator
)
