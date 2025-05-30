package app.meetacy.backend.feature.auth.endpoints.generate

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.types.serializable.access.AccessIdentity
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class GenerateParam(val nickname: String)

interface TokenGenerateRepository {
    suspend fun generateToken(nickname: String): TokenGenerateResult
}

sealed interface TokenGenerateResult {
    data class Success(val accessIdentity: AccessIdentity) : TokenGenerateResult
    data object InvalidUtf8String : TokenGenerateResult
}

fun Route.generateToken(repository: TokenGenerateRepository) = post("/generate") {
    val generateParam = call.receive<GenerateParam>()

    when (val result = repository.generateToken(generateParam.nickname)) {
        is TokenGenerateResult.Success -> call.respondSuccess(result.accessIdentity)
        is TokenGenerateResult.InvalidUtf8String -> call.respondFailure(Failure.InvalidUtf8String)
    }
}
