package app.meetacy.backend.application.endpoints.deeplink.user

import app.meetacy.backend.endpoint.ktor.respondSuccess
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.userDeeplinks() = get("/u/{username}") {
    val userId = call.parameters["username"] ?: return@get call.respondSuccess()
    call.respondRedirect("meetacy://view/user?username=$userId")
}
