package app.meetacy.backend.feature.email.database.integration

import app.meetacy.backend.feature.email.database.ConfirmationStorage
import app.meetacy.backend.feature.email.database.DatabaseEmailSender
import app.meetacy.backend.feature.email.database.DatabaseEmailText
import app.meetacy.backend.feature.users.database.users.UsersStorage
import app.meetacy.backend.types.users.UserId
import app.meetacy.backend.feature.email.usecase.LinkEmailUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseLinkEmailStorage(db: Database) : LinkEmailUsecase.Storage {
    private val confirmationStorage = ConfirmationStorage(db)
    private val usersStorage = UsersStorage(db)

    override suspend fun isEmailOccupied(email: String): Boolean =
        usersStorage.isEmailOccupied(email)

    override suspend fun updateEmail(userId: UserId, email: String) {
        usersStorage.updateEmail(userId, email)
    }

    override suspend fun addConfirmationHash(userId: UserId, email: String, confirmationHash: String) {
        confirmationStorage.addHash(userId, email, confirmationHash)
    }
}

object DatabaseLinkEmailMailer : LinkEmailUsecase.Mailer {
    override fun sendEmailOccupiedMessage(email: String) =
        DatabaseEmailSender.sendEmail(email, DatabaseEmailText.getOccupiedText())

    override fun sendConfirmationMessage(email: String, confirmationHash: String) =
        DatabaseEmailSender.sendEmail(email, DatabaseEmailText.getConfirmationText(email, confirmationHash))
}