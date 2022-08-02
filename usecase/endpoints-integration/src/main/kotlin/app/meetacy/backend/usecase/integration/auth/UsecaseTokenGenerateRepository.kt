package app.meetacy.backend.usecase.integration.auth

import app.meetacy.backend.endpoint.auth.generate.TokenGenerateRepository
import app.meetacy.backend.types.AccessToken
import app.meetacy.backend.usecase.auth.GenerateTokenUsecase

class UsecaseTokenGenerateRepository(private val usecase: GenerateTokenUsecase): TokenGenerateRepository {
    override suspend fun generateToken(nickname: String): AccessToken =
        usecase.generateToken(nickname)
}
