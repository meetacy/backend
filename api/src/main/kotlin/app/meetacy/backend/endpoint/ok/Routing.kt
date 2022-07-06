package app.meetacy.backend.endpoint.ok

import io.ktor.server.application.call
import io.ktor.server.response.respondText
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get

fun Routing.ok() = get("/OK") {
    call.respondText("OK")
}
