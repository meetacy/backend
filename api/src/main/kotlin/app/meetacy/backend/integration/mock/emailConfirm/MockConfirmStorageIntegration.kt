package app.meetacy.backend.integration.mock.emailConfirm

import app.meetacy.backend.endpoint.auth.email.confirm.ConfirmHashResult
import app.meetacy.backend.endpoint.auth.email.confirm.ConfirmStorage
import app.meetacy.backend.mock.storage.ConfirmationStorage
import app.meetacy.backend.mock.storage.UsersStorage

object MockConfirmStorageIntegration : ConfirmStorage {
    override suspend fun checkConfirmHash(email: String, confirmHash: String): ConfirmHashResult =
        when (ConfirmationStorage.isValidConfirmHash(email, confirmHash)) {
            true -> {
                val userId = UsersStorage.getUser(email)!!.id
                UsersStorage.verifyEmail(userId)
                ConfirmationStorage.deleteHash(email, confirmHash)
                ConfirmHashResult.Success
            }
            false -> ConfirmHashResult.LinkInvalid
        }
}
