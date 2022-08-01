package app.meetacy.backend.usecase.email

import app.meetacy.backend.types.AccessToken
import app.meetacy.backend.types.UserId
import app.meetacy.backend.usecase.types.HashGenerator

class LinkEmailUsecase(
    private val storage: app.meetacy.backend.usecase.email.LinkEmailUsecase.Storage,
    private val mailer: app.meetacy.backend.usecase.email.LinkEmailUsecase.Mailer,
    private val hashGenerator: HashGenerator
) {
    sealed interface LinkResult {
        object Success : app.meetacy.backend.usecase.email.LinkEmailUsecase.LinkResult
        object TokenInvalid : app.meetacy.backend.usecase.email.LinkEmailUsecase.LinkResult
    }

    suspend fun linkEmail(email: String, token: AccessToken): app.meetacy.backend.usecase.email.LinkEmailUsecase.LinkResult {
        val userId = storage.getUserId(token) ?: return app.meetacy.backend.usecase.email.LinkEmailUsecase.LinkResult.TokenInvalid

        if (storage.isEmailOccupied(email)) {
            mailer.sendEmailOccupiedMessage(email)
            return app.meetacy.backend.usecase.email.LinkEmailUsecase.LinkResult.Success
        }

        val confirmationHash = hashGenerator.generate()

        storage.updateEmail(userId, email)
        storage.addConfirmationHash(userId, email, confirmationHash)

        mailer.sendConfirmationMessage(email, confirmationHash)

        return app.meetacy.backend.usecase.email.LinkEmailUsecase.LinkResult.Success
    }

    interface Storage {
        suspend fun isEmailOccupied(email: String): Boolean
        suspend fun getUserId(token: AccessToken): UserId?
        suspend fun updateEmail(userId: UserId, email: String)
        suspend fun addConfirmationHash(userId: UserId, email: String, confirmationHash: String)
    }

    interface Mailer {
        fun sendEmailOccupiedMessage(email: String)
        fun sendConfirmationMessage(email: String, confirmationHash: String)
    }
}
