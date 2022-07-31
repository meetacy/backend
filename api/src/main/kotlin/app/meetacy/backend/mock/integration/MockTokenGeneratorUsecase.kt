package app.meetacy.backend.mock.integration

import app.meetacy.backend.domain.AccessToken
import app.meetacy.backend.domain.UserId
import app.meetacy.backend.mock.storage.TokensStorage
import app.meetacy.backend.usecase.auth.GenerateTokenUsecase

private object MockTokenGenerateStorage : GenerateTokenUsecase.Storage {
    override suspend fun createUser(nickname: String): UserId =
        mockCreateUserUsecase().createUser(nickname)

    override suspend fun addToken(id: UserId, token: AccessToken) =
        TokensStorage.addToken(id, token)
}

fun mockGenerateTokenUsecase(): GenerateTokenUsecase = GenerateTokenUsecase(
    MockTokenGenerateStorage,
    MockHashGeneratorIntegration
)
