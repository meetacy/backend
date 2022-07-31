@file:UseSerializers(AccessTokenSerializer::class)

package app.meetacy.backend.endpoint.auth.generate

import app.meetacy.backend.domain.AccessToken
import app.meetacy.backend.serialization.AccessTokenSerializer
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
    val result: AccessToken
)

interface TokenGenerator {
    suspend fun generateToken(nickname: String): AccessToken
}

fun Route.generateToken(tokenGenerator: TokenGenerator) = post ("/generate") {
    val generateParam = call.receive<GenerateParam>()
    val token = tokenGenerator.generateToken(generateParam.nickname)
    call.respond(GenerateTokenResponse(status = true, result = token))
}
