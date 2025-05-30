package app.meetacy.backend.feature.email.usecase

import app.meetacy.backend.types.users.UserId

class ConfirmEmailUsecase(private val storage: Storage) {
    sealed interface ConfirmResult {
        data object Success : ConfirmResult
        data object LinkInvalid : ConfirmResult
    }

    suspend fun confirm(email: String, confirmHash: String): ConfirmResult {
        val userId = storage.getConfirmHashOwnerId(email, confirmHash) ?:
            return ConfirmResult.LinkInvalid

        storage.verifyEmail(userId)
        storage.deleteHashes(email)

        return ConfirmResult.Success
    }

    interface Storage {
        /**
         * @return null if you confirm hash invalid
         */
        suspend fun getConfirmHashOwnerId(email: String, confirmHash: String): UserId?
        suspend fun deleteHashes(email: String)
        suspend fun verifyEmail(userIdentity: UserId)
    }
}
