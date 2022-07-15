package app.meetacy.backend.mock.integration

import app.meetacy.backend.mock.storage.ConfirmationStorage
import app.meetacy.backend.mock.storage.UsersStorage
import app.meetacy.backend.usecase.email.ConfirmEmailUsecase

private object MockConfirmEmailStorage : ConfirmEmailUsecase.Storage {

    override suspend fun getConfirmHashOwnerId(email: String, confirmHash: String): Long? =
        ConfirmationStorage.getConfirmHashOwnerId(email, confirmHash)

    override suspend fun deleteHashes(email: String) {
        ConfirmationStorage.deleteHashes(email)
    }

    override suspend fun verifyEmail(userId: Long) {
        UsersStorage.verifyEmail(userId)
    }

}

fun mockConfirmEmailUsecase(): ConfirmEmailUsecase = ConfirmEmailUsecase(MockConfirmEmailStorage)
