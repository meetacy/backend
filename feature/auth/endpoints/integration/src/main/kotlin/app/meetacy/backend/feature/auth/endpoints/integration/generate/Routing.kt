package app.meetacy.backend.feature.auth.endpoints.integration.generate

import app.meetacy.backend.feature.auth.endpoints.generate.TokenGenerateRepository
import app.meetacy.backend.feature.auth.endpoints.generate.TokenGenerateResult
import app.meetacy.backend.feature.auth.endpoints.generate.generateToken
import app.meetacy.backend.feature.auth.usecase.GenerateTokenUsecase
import app.meetacy.backend.types.serializable.access.serializable
import app.meetacy.di.DI
import io.ktor.server.routing.*

internal fun Route.generateToken(di: DI) {
    val generateTokenUsecase: GenerateTokenUsecase by di.getting

    val tokenGenerateRepository = object : TokenGenerateRepository {
        override suspend fun generateToken(nickname: String): TokenGenerateResult {
            return when(val result = generateTokenUsecase.generateToken(nickname)) {
                GenerateTokenUsecase.Result.InvalidUtf8String ->
                    TokenGenerateResult.InvalidUtf8String
                is GenerateTokenUsecase.Result.Success ->
                    TokenGenerateResult.Success(result.accessIdentity.serializable())
            }
        }
    }

    generateToken(tokenGenerateRepository)
}
