package app.meetacy.backend.database.integration.email

import app.meetacy.backend.database.email.ConfirmationTable
import app.meetacy.backend.database.email.DatabaseEmailSender
import app.meetacy.backend.database.email.DatabaseEmailText
import app.meetacy.backend.database.users.UsersTable
import app.meetacy.backend.types.AccessIdentity
import app.meetacy.backend.types.UserId
import app.meetacy.backend.usecase.email.LinkEmailUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseLinkEmailStorage(db: Database) : LinkEmailUsecase.Storage {
    private val confirmationTable = ConfirmationTable(db)
    private val usersTable = UsersTable(db)

    override suspend fun isEmailOccupied(email: String): Boolean =
        usersTable.isEmailOccupied(email)

    override suspend fun updateEmail(userId: UserId, email: String) {
        usersTable.updateEmail(userId, email)
    }

    override suspend fun addConfirmationHash(userId: UserId, email: String, confirmationHash: String) {
        confirmationTable.addHash(userId, email, confirmationHash)
    }
}

object DatabaseLinkEmailMailer : LinkEmailUsecase.Mailer {
    override fun sendEmailOccupiedMessage(email: String) =
        DatabaseEmailSender.sendEmail(email, DatabaseEmailText.getOccupiedText())

    override fun sendConfirmationMessage(email: String, confirmationHash: String) =
        DatabaseEmailSender.sendEmail(email, DatabaseEmailText.getConfirmationText(email, confirmationHash))
}