package app.meetacy.backend.mock.integration

import app.meetacy.backend.mock.generator.MockHashGenerator
import app.meetacy.backend.mock.storage.TokensStorage
import app.meetacy.backend.usecase.auth.GenerateTokenUsecase

private object MockTokenGenerateStorage : GenerateTokenUsecase.Storage {
    override suspend fun createUser(nickname: String): Long =
        mockCreateUserUsecase().createUser(nickname)

    override suspend fun addToken(id: Long, token: String) =
        TokensStorage.addToken(id, token)
}

private object MockTokenGenerateTokenGenerator : GenerateTokenUsecase.TokenGenerator {
    override fun generateToken(): String =
        MockHashGenerator.generate()
}

fun mockGenerateTokenUsecase(): GenerateTokenUsecase = GenerateTokenUsecase(
    MockTokenGenerateStorage,
    MockTokenGenerateTokenGenerator
)
