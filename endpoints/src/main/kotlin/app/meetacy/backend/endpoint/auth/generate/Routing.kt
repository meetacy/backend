package app.meetacy.backend.endpoint.auth.generate

import app.meetacy.backend.endpoint.types.ServerFailure
import app.meetacy.backend.endpoint.types.ServerResponse
import app.meetacy.backend.types.AccessIdentity
import app.meetacy.backend.types.serialization.AccessIdentitySerializable
import app.meetacy.backend.types.serialization.serializable
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class GenerateParam(
    val nickname: String
)

@Serializable
data class GenerateTokenResponse(
    val status: Boolean,
    val errorCode: Int? = null,
    val errorMessage: String? = null,
    val result: AccessIdentitySerializable?
)

interface TokenGenerateRepository {
    suspend fun generateToken(nickname: String): TokenGenerateResult
}

sealed interface TokenGenerateResult {
    class Success(val accessIdentity: AccessIdentity) : TokenGenerateResult
    object InvalidUtf8String : TokenGenerateResult
}

fun Route.generateToken(tokenGenerateRepository: TokenGenerateRepository) = post ("/generate") {
    val generateParam = call.receive<GenerateParam>()
    when(val result = tokenGenerateRepository.generateToken(generateParam.nickname)) {
        is TokenGenerateResult.Success -> {
            call.respond(
                ServerResponse(result.accessIdentity.serializable())
            )
        }
        TokenGenerateResult.InvalidUtf8String -> {
            call.respond(
                ServerFailure(1, "Please provide a valid nickname")
            )
        }
    }
}
