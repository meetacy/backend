package app.meetacy.backend.usecase.integration.auth

import app.meetacy.backend.endpoint.auth.generate.TokenGenerateRepository
import app.meetacy.backend.types.AccessIdentity
import app.meetacy.backend.usecase.auth.GenerateTokenUsecase

class UsecaseTokenGenerateRepository(private val usecase: GenerateTokenUsecase): TokenGenerateRepository {
    override suspend fun generateToken(nickname: String): AccessIdentity =
        usecase.generateToken(nickname)
}
