package app.meetacy.backend.database.integration.email

import app.meetacy.backend.database.email.ConfirmationTable
import app.meetacy.backend.types.UserId
import app.meetacy.backend.usecase.email.ConfirmEmailUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseConfirmEmailStorage(database: Database) : ConfirmEmailUsecase.Storage {
    private val confirmationTable = ConfirmationTable(database)

    override suspend fun getConfirmHashOwnerId(email: String, confirmHash: String): UserId? =
        confirmationTable.getConfirmHashOwnerId(email, confirmHash)

    override suspend fun deleteHashes(email: String) =
        confirmationTable.deleteHashes(email)

    override suspend fun verifyEmail(userId: UserId) {
        TODO("Not yet implemented")
    }
}
