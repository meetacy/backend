package app.meetacy.backend.feature.email.usecase

import app.meetacy.backend.types.generator.AccessHashGenerator
import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.types.auth.authorizeWithUserId
import app.meetacy.backend.types.users.UserId

class LinkEmailUsecase(
    private val storage: Storage,
    private val mailer: Mailer,
    private val hashGenerator: AccessHashGenerator,
    private val authRepository: AuthRepository
) {
    sealed interface LinkResult {
        data object Success : LinkResult
        data object TokenInvalid : LinkResult
    }

    suspend fun linkEmail(email: String, token: AccessIdentity): LinkResult {
        val userId = authRepository.authorizeWithUserId(token)  { return LinkResult.TokenInvalid }

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
        suspend fun updateEmail(userId: UserId, email: String)
        suspend fun addConfirmationHash(userId: UserId, email: String, confirmationHash: String)
    }

    interface Mailer {
        fun sendEmailOccupiedMessage(email: String)
        fun sendConfirmationMessage(email: String, confirmationHash: String)
    }
}
