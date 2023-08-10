package app.meetacy.backend.feature.auth.usecase.integration

import app.meetacy.backend.feature.auth.endpoints.generate.TokenGenerateRepository
import app.meetacy.backend.feature.auth.endpoints.generate.TokenGenerateResult
import app.meetacy.backend.types.serializable.access.serializable
import app.meetacy.backend.feature.auth.usecase.auth.GenerateTokenUsecase

class UsecaseTokenGenerateRepository(private val usecase: app.meetacy.backend.feature.auth.usecase.auth.GenerateTokenUsecase): TokenGenerateRepository {
    override suspend fun generateToken(nickname: String): TokenGenerateResult {
        return when(val result = usecase.generateToken(nickname)) {
            app.meetacy.backend.feature.auth.usecase.auth.GenerateTokenUsecase.Result.InvalidUtf8String ->
                TokenGenerateResult.InvalidUtf8String
            is app.meetacy.backend.feature.auth.usecase.auth.GenerateTokenUsecase.Result.Success ->
                TokenGenerateResult.Success(result.accessIdentity.serializable())
        }
    }
}
