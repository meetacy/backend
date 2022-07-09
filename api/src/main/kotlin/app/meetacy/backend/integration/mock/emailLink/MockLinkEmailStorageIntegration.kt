package app.meetacy.backend.integration.mock.emailLink

import app.meetacy.backend.endpoint.auth.email.link.ConfirmHashResult
import app.meetacy.backend.endpoint.auth.email.link.LinkEmailStorage
import app.meetacy.backend.mock.storage.ConfirmationStorage
import app.meetacy.backend.mock.storage.TokensStorage
import app.meetacy.backend.mock.storage.UsersStorage

interface ConfirmHashGenerator {
    fun generate(): String
}

class MockLinkEmailStorageIntegration(private val generator: ConfirmHashGenerator) : LinkEmailStorage {
    override suspend fun registerConfirmHash(token: String, email: String): ConfirmHashResult {
        val tokenModel = TokensStorage.getToken(token) ?:
            return ConfirmHashResult.TokenInvalid

        val userId = tokenModel.ownerId
        UsersStorage.updateEmail(userId, email)

        val hash = generator.generate()

        ConfirmationStorage.addHash(email, hash)

        return ConfirmHashResult.Success(hash)
    }
}
