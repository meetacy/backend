package app.meetacy.backend.endpoint

import io.ktor.server.application.call
import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

fun startEndpoints(port: Int, wait: Boolean) =
    embeddedServer(CIO, port) {
        routing {
            get {
                call.respond("OK")
            }
        }
    }.start(wait)
