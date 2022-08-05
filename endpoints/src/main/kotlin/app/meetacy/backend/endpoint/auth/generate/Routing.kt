package app.meetacy.backend.endpoint.auth.generate

import app.meetacy.backend.types.AccessToken
import app.meetacy.backend.types.serialization.AccessTokenSerializable
import app.meetacy.backend.types.serialization.serializable
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers

@Serializable
data class GenerateParam(
    val nickname: String
)

@Serializable
data class GenerateTokenResponse(
    val status: Boolean,
    val result: AccessTokenSerializable
)

interface TokenGenerateRepository {
    suspend fun generateToken(nickname: String): AccessToken
}

fun Route.generateToken(tokenGenerateRepository: TokenGenerateRepository) = post ("/generate") {
    val generateParam = call.receive<GenerateParam>()
    val token = tokenGenerateRepository.generateToken(generateParam.nickname)
    call.respond(GenerateTokenResponse(status = true, result = token.serializable()))
}
