package app.meetacy.backend.endpoint.auth.generate

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
    val result: String
)

interface TokenGenerator {
    fun generateToken(nickname: String): String
}

fun Route.generateToken(tokenGenerator: TokenGenerator) = post ("/generate") {
    val generateParam = call.receive<GenerateParam>()
    val token = tokenGenerator.generateToken(generateParam.nickname)
    call.respond(GenerateTokenResponse(token))
}
