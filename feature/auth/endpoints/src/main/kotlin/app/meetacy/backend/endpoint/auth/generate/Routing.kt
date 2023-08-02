package app.meetacy.backend.endpoint.auth.generate

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.di.global.di
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class GenerateParam(val nickname: String)

fun interface TokenGenerateRepository {
    suspend fun generateToken(nickname: String): TokenGenerateResult
}

sealed interface TokenGenerateResult {
    data class Success(val accessIdentity: AccessIdentity) : TokenGenerateResult
    data object InvalidUtf8String : TokenGenerateResult
}

fun Route.generateToken() {
    val repository: TokenGenerateRepository by di.getting

    post("/generate") {
        val generateParam = call.receive<GenerateParam>()

        when (val result = repository.generateToken(generateParam.nickname)) {
            is TokenGenerateResult.Success -> call.respondSuccess(result.accessIdentity)
            is TokenGenerateResult.InvalidUtf8String -> call.respondFailure(Failure.InvalidUtf8String)
        }
    }
}
