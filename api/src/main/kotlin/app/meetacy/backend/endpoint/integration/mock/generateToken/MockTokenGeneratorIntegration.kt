package app.meetacy.backend.endpoint.integration.mock.generateToken

import app.meetacy.backend.endpoint.auth.generate.TokenGenerator
import app.meetacy.backend.mock.storage.MockUser
import app.meetacy.backend.mock.storage.TokensStorage

interface UserGenerator {
    fun generateUser(nickname: String): MockUser
}

interface HashGenerator {
    fun generateHash(): String
}

class MockTokenGeneratorIntegration(
    private val userGenerator: UserGenerator,
    private val hashGenerator: HashGenerator
) : TokenGenerator {
    override fun generateToken(nickname: String): String {
        val newUser = userGenerator.generateUser(nickname)

        val token = hashGenerator.generateHash()

        TokensStorage.addToken(
            userId = newUser.id,
            token = token,
        )

        return token
    }
}
