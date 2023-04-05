package app.meetacy.backend.database.integration.email

import app.meetacy.backend.database.email.ConfirmationTable
import app.meetacy.backend.database.users.UsersTable
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.email.ConfirmEmailUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseConfirmEmailStorage(database: Database) : ConfirmEmailUsecase.Storage {
    private val confirmationTable = ConfirmationTable(database)
    private val usersTable = UsersTable(database)

    override suspend fun getConfirmHashOwnerId(email: String, confirmHash: String): UserId? =
        confirmationTable.getConfirmHashOwnerId(email, confirmHash)

    override suspend fun deleteHashes(email: String) =
        confirmationTable.deleteHashes(email)

    override suspend fun verifyEmail(userIdentity: UserId) {
        usersTable.verifyEmail(userIdentity)
    }
}
