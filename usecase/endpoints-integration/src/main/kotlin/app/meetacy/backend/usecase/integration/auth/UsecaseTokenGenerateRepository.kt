package app.meetacy.backend.usecase.integration.auth

import app.meetacy.backend.endpoint.auth.generate.TokenGenerateRepository
import app.meetacy.backend.endpoint.auth.generate.TokenGenerateResult
import app.meetacy.backend.usecase.auth.GenerateTokenUsecase

class UsecaseTokenGenerateRepository(private val usecase: GenerateTokenUsecase): TokenGenerateRepository {
    override suspend fun generateToken(nickname: String): TokenGenerateResult {
        return when(val result = usecase.generateToken(nickname)) {
            GenerateTokenUsecase.Result.InvalidUtf8String ->
                TokenGenerateResult.InvalidUtf8String
            is GenerateTokenUsecase.Result.Success ->
                TokenGenerateResult.Success(result.accessIdentity)
        }
    }
}
