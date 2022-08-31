package app.meetacy.backend.endpoint.auth.generate

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
    val result: AccessIdentitySerializable
)

interface TokenGenerateRepository {
    suspend fun generateToken(nickname: String): AccessIdentity
}

fun Route.generateToken(tokenGenerateRepository: TokenGenerateRepository) = post ("/generate") {
    val generateParam = call.receive<GenerateParam>()
    val token = tokenGenerateRepository.generateToken(generateParam.nickname)
    call.respond(GenerateTokenResponse(status = true, result = token.serializable()))
}
