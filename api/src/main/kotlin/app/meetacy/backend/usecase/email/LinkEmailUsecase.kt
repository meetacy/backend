package app.meetacy.backend.usecase.email

class LinkEmailUsecase(
    private val storage: Storage,
    private val mailer: Mailer,
    private val hashGenerator: HashGenerator
) {
    sealed interface LinkResult {
        object Success : LinkResult
        object TokenInvalid : LinkResult
    }

    suspend fun linkEmail(email: String, token: String): LinkResult {
        val userId = storage.getUserId(token) ?: return LinkResult.TokenInvalid

        if (storage.isEmailOccupied(email)) {
            mailer.sendEmailOccupiedMessage(email)
            return LinkResult.Success
        }

        val confirmationHash = hashGenerator.generate()

        storage.udateEmail(userId, email)
        storage.addConfirmationHash(userId, email, confirmationHash)

        mailer.sendConfirmationMessage(email, confirmationHash)

        return LinkResult.Success
    }

    interface Storage {
        suspend fun isEmailOccupied(email: String): Boolean
        suspend fun getUserId(token: String): Long?
        suspend fun udateEmail(userId: Long, email: String)
        suspend fun addConfirmationHash(userId: Long, email: String, confirmationHash: String)
    }

    interface Mailer {
        fun sendEmailOccupiedMessage(email: String)
        fun sendConfirmationMessage(email: String, confirmationHash: String)
    }

    interface HashGenerator {
        fun generate(): String
    }
}
