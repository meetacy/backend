package app.meetacy.backend.endpoint.meet.get

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class GetParam(
    val meetingId: Long,
    val meetingAccessHash: String

)

fun Route.getMeet() = post("/get") {
    val params = call.receive<GetParam>()
}
