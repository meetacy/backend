package app.meetacy.backend.feature.email.database.integration

import app.meetacy.backend.feature.email.database.ConfirmationStorage
import app.meetacy.backend.feature.users.database.users.UsersStorage
import app.meetacy.backend.types.users.UserId
import app.meetacy.backend.feature.email.usecase.ConfirmEmailUsecase
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
