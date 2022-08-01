package app.meetacy.backend.usecase.email

import app.meetacy.backend.types.UserId

class ConfirmEmailUsecase(private val storage: app.meetacy.backend.usecase.email.ConfirmEmailUsecase.Storage) {
    sealed interface ConfirmResult {
        object Success : app.meetacy.backend.usecase.email.ConfirmEmailUsecase.ConfirmResult
        object LinkInvalid : app.meetacy.backend.usecase.email.ConfirmEmailUsecase.ConfirmResult
    }

    suspend fun confirm(email: String, confirmHash: String): app.meetacy.backend.usecase.email.ConfirmEmailUsecase.ConfirmResult {
        val userId = storage.getConfirmHashOwnerId(email, confirmHash) ?:
            return app.meetacy.backend.usecase.email.ConfirmEmailUsecase.ConfirmResult.LinkInvalid

        storage.verifyEmail(userId)
        storage.deleteHashes(email)

        return app.meetacy.backend.usecase.email.ConfirmEmailUsecase.ConfirmResult.Success
    }

    interface Storage {
        /**
         * @return null if confirm hash invalid
         */
        suspend fun getConfirmHashOwnerId(email: String, confirmHash: String): UserId?
        suspend fun deleteHashes(email: String)
        suspend fun verifyEmail(userId: UserId)
    }
}
