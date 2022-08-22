package app.meetacy.backend.mock.integration

import app.meetacy.backend.database.ConfirmationTable
import app.meetacy.backend.mock.email.MockEmailSender
import app.meetacy.backend.types.AccessToken
import app.meetacy.backend.types.UserId
import app.meetacy.backend.mock.email.MockEmailText
import app.meetacy.backend.mock.storage.ConfirmationStorage
import app.meetacy.backend.mock.storage.TokensStorage
import app.meetacy.backend.mock.storage.UsersStorage
import app.meetacy.backend.usecase.email.LinkEmailUsecase
import org.jetbrains.exposed.sql.Database

class MockLinkEmailStorage(private val db: Database) : LinkEmailUsecase.Storage {
    private val confirmationTable = ConfirmationTable(db)

    override suspend fun isEmailOccupied(email: String): Boolean =
        UsersStorage.isEmailOccupied(email)

    override suspend fun getUserId(token: AccessToken): UserId? =
        TokensStorage.getToken(token)?.ownerId

    override suspend fun updateEmail(userId: UserId, email: String) {
        UsersStorage.updateEmail(userId, email)
    }

    override suspend fun addConfirmationHash(userId: UserId, email: String, confirmationHash: String) {
        confirmationTable.addHash(userId, email, confirmationHash)
    }
}

object MockLinkEmailMailer : LinkEmailUsecase.Mailer {
    override fun sendEmailOccupiedMessage(email: String) =
        MockEmailSender.sendEmail(email, MockEmailText.getOccupiedText())

    override fun sendConfirmationMessage(email: String, confirmationHash: String) =
        MockEmailSender.sendEmail(email, MockEmailText.getConfirmationText(email, confirmationHash))
}
