package app.meetacy.backend.usecase.email

import app.meetacy.backend.types.user.UserId

class ConfirmEmailUsecase(private val storage: Storage) {
    sealed interface ConfirmResult {
        object Success : ConfirmResult
        object LinkInvalid : ConfirmResult
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
         * @return null if confirm hash invalid
         */
        suspend fun getConfirmHashOwnerId(email: String, confirmHash: String): UserId?
        suspend fun deleteHashes(email: String)
        suspend fun verifyEmail(userIdentity: UserId)
    }
}
