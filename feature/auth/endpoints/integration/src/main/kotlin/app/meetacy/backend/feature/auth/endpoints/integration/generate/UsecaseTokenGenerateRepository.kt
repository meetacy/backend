package app.meetacy.backend.feature.auth.endpoints.integration.generate

import app.meetacy.backend.feature.auth.endpoints.generate.TokenGenerateRepository
import app.meetacy.backend.feature.auth.endpoints.generate.TokenGenerateResult
import app.meetacy.backend.types.serializable.access.serializable
import app.meetacy.backend.feature.auth.usecase.GenerateTokenUsecase
import app.meetacy.di.builder.DIBuilder

internal fun DIBuilder.tokenGenerator() {
    val tokenGenerateRepository by singleton<TokenGenerateRepository> {
        UsecaseTokenGenerateRepository(
            usecase = get()
        )
    }
}

private class UsecaseTokenGenerateRepository(private val usecase: GenerateTokenUsecase): TokenGenerateRepository {
    override suspend fun generateToken(nickname: String): TokenGenerateResult {
        return when(val result = usecase.generateToken(nickname)) {
            GenerateTokenUsecase.Result.InvalidUtf8String ->
                TokenGenerateResult.InvalidUtf8String
            is GenerateTokenUsecase.Result.Success ->
                TokenGenerateResult.Success(result.accessIdentity.serializable())
        }
    }
}
