package app.meetacy.backend.mock.integration.email

import app.meetacy.backend.types.UserId
import app.meetacy.backend.mock.storage.ConfirmationStorage
import app.meetacy.backend.mock.storage.UsersStorage
import app.meetacy.backend.usecase.email.ConfirmEmailUsecase

object MockConfirmEmailStorage : ConfirmEmailUsecase.Storage {

    override suspend fun getConfirmHashOwnerId(email: String, confirmHash: String): UserId? =
        ConfirmationStorage.getConfirmHashOwnerId(email, confirmHash)

    override suspend fun deleteHashes(email: String) {
        ConfirmationStorage.deleteHashes(email)
    }

    override suspend fun verifyEmail(userId: UserId) {
        UsersStorage.verifyEmail(userId)
    }
}
