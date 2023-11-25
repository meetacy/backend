package app.meetacy.backend.feature.auth.endpoints.integration.generate

import app.meetacy.backend.feature.auth.endpoints.generate.TokenGenerateRepository
import app.meetacy.backend.feature.auth.endpoints.generate.TokenGenerateResult
import app.meetacy.backend.feature.auth.endpoints.generate.generateToken
import app.meetacy.backend.feature.auth.usecase.GenerateAuthUsecase
import app.meetacy.backend.types.serializable.access.serializable
import app.meetacy.di.DI
import io.ktor.server.routing.Route

internal fun Route.generateToken(di: DI) {
    val generateAuthUsecase: GenerateAuthUsecase by di.getting

    val tokenGenerateRepository = object : TokenGenerateRepository {
        override suspend fun generateToken(nickname: String): TokenGenerateResult {
            return when(val result = generateAuthUsecase.generateAuth(nickname)) {
                GenerateAuthUsecase.Result.InvalidUtf8String ->
                    TokenGenerateResult.InvalidUtf8String
                is GenerateAuthUsecase.Result.Success ->
                    TokenGenerateResult.Success(result.accessIdentity.serializable())
            }
        }
    }

    generateToken(tokenGenerateRepository)
}
