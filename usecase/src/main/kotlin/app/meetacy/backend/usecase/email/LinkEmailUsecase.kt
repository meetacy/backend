package app.meetacy.backend.usecase.email

import app.meetacy.backend.types.AccessToken
import app.meetacy.backend.types.UserId
import app.meetacy.backend.types.UserIdentity
import app.meetacy.backend.usecase.types.AuthRepository
import app.meetacy.backend.usecase.types.HashGenerator
import app.meetacy.backend.usecase.types.authorize

class LinkEmailUsecase(
    private val storage: Storage,
    private val mailer: Mailer,
    private val hashGenerator: HashGenerator,
    private val authRepository: AuthRepository
) {
    sealed interface LinkResult {
        object Success : LinkResult
        object TokenInvalid : LinkResult
    }

    suspend fun linkEmail(email: String, token: AccessToken): LinkResult {
        val userId = authRepository.authorize(token)  { return LinkResult.TokenInvalid }

        if (storage.isEmailOccupied(email)) {
            mailer.sendEmailOccupiedMessage(email)
            return LinkResult.Success
        }

        val confirmationHash = hashGenerator.generate()

        storage.updateEmail(userId, email)
        storage.addConfirmationHash(userId, email, confirmationHash)

        mailer.sendConfirmationMessage(email, confirmationHash)

        return LinkResult.Success
    }

    interface Storage {
        suspend fun isEmailOccupied(email: String): Boolean
        suspend fun getUserId(token: AccessToken): UserId?
        suspend fun updateEmail(userIdentity: UserId, email: String)
        suspend fun addConfirmationHash(userIdentity: UserId, email: String, confirmationHash: String)
    }

    interface Mailer {
        fun sendEmailOccupiedMessage(email: String)
        fun sendConfirmationMessage(email: String, confirmationHash: String)
    }
}
