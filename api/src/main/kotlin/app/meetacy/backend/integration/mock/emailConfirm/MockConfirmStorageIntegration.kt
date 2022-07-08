package app.meetacy.backend.integration.mock.emailConfirm

import app.meetacy.backend.endpoint.auth.email.confirm.ConfirmHashResult
import app.meetacy.backend.endpoint.auth.email.confirm.ConfirmStorage
import app.meetacy.backend.mock.storage.ConfirmationStorage
import app.meetacy.backend.mock.storage.UsersStorage

object MockConfirmStorageIntegration : ConfirmStorage {
    override suspend fun checkConfirmHash(userId: Int, email: String, confirmHash: String): ConfirmHashResult =
        when (ConfirmationStorage.isValidConfirmHash(userId, email, confirmHash)) {
            true -> {
                UsersStorage.verifyEmail(userId)
                ConfirmationStorage.deleteHash(userId, email, confirmHash)
                ConfirmHashResult.Success
            }
            false -> ConfirmHashResult.LinkInvalid
        }
}
