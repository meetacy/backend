package app.meetacy.backend.mock.integration

import app.meetacy.backend.mock.integration.users.MockCreateUserStorage
import app.meetacy.backend.types.AccessToken
import app.meetacy.backend.types.UserId
import app.meetacy.backend.mock.storage.TokensStorage
import app.meetacy.backend.usecase.auth.GenerateTokenUsecase
import app.meetacy.backend.usecase.types.HashGenerator
import app.meetacy.backend.usecase.users.CreateUserUsecase

class MockGenerateTokenStorage(private val hashGenerator: HashGenerator) : GenerateTokenUsecase.Storage {
    override suspend fun createUser(nickname: String): UserId =
        CreateUserUsecase(hashGenerator, MockCreateUserStorage).createUser(nickname)

    override suspend fun addToken(id: UserId, token: AccessToken) =
        TokensStorage.addToken(id, token)
}
