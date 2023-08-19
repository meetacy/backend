package app.meetacy.backend.feature.auth.endpoints.integration.generate

import app.meetacy.backend.feature.auth.endpoints.generate.TokenGenerateRepository
import app.meetacy.backend.feature.auth.endpoints.generate.TokenGenerateResult
import app.meetacy.backend.types.serializable.access.serializable
import app.meetacy.backend.feature.auth.usecase.GenerateTokenUsecase
import app.meetacy.di.builder.DIBuilder

internal fun DIBuilder.tokenGenerator() {
    val tokenGenerateRepository by singleton<TokenGenerateRepository> {
        val generateTokenUsecase: GenerateTokenUsecase by getting

        object : TokenGenerateRepository {
            override suspend fun generateToken(nickname: String): TokenGenerateResult {
                return when(val result = generateTokenUsecase.generateToken(nickname)) {
                    GenerateTokenUsecase.Result.InvalidUtf8String ->
                        TokenGenerateResult.InvalidUtf8String
                    is GenerateTokenUsecase.Result.Success ->
                        TokenGenerateResult.Success(result.accessIdentity.serializable())
                }
            }
        }
    }
}
