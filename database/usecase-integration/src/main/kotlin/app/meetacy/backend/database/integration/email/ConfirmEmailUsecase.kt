package app.meetacy.backend.database.integration.email

import app.meetacy.backend.database.email.ConfirmationStorage
import app.meetacy.backend.database.users.UsersStorage
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.email.ConfirmEmailUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseConfirmEmailStorage(database: Database) : ConfirmEmailUsecase.Storage {
    private val confirmationStorage = ConfirmationStorage(database)
    private val usersStorage = UsersStorage(database)

    override suspend fun getConfirmHashOwnerId(email: String, confirmHash: String): UserId? =
        confirmationStorage.getConfirmHashOwnerId(email, confirmHash)

    override suspend fun deleteHashes(email: String) =
        confirmationStorage.deleteHashes(email)

    override suspend fun verifyEmail(userIdentity: UserId) {
        usersStorage.verifyEmail(userIdentity)
    }
}
