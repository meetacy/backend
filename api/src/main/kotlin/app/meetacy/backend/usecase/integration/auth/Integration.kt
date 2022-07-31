package app.meetacy.backend.usecase.integration.auth

import app.meetacy.backend.domain.AccessToken
import app.meetacy.backend.endpoint.auth.generate.TokenGenerator
import app.meetacy.backend.usecase.auth.GenerateTokenUsecase

private class Integration(
    private val usecase: GenerateTokenUsecase
): TokenGenerator {
    override suspend fun generateToken(nickname: String): AccessToken =
        usecase.generateToken(nickname)
}

fun usecaseTokenGenerator(usecase: GenerateTokenUsecase): TokenGenerator =
    Integration(usecase)
