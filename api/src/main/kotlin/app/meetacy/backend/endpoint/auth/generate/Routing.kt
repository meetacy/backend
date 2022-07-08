package app.meetacy.backend.endpoint.auth.generate

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

data class GenerateParam(
    val nickname: String
)

interface TokenGeneration {
    fun generateToken(nickname: GenerateParam): String
}

fun Route.getToken(tokenGeneration: TokenGeneration) = post ("/generate") {
    val generateParam = call.receive<GenerateParam>()
    val token = tokenGeneration.generateToken(generateParam)
    call.respond(token)
}