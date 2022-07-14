package app.meetacy.backend.endpoint.meet.create

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class CreateParam(
    val accessToken: String
)

fun Route.createMeet() = post("/create") {
    val params = call.receive<CreateParam>()
}
