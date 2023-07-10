package app.meetacy.backend.endpoint.exceptions

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.plugins.statuspages.*

fun Application.installExceptionsHandler() {
    install(StatusPages) {
        exception { call: ApplicationCall, throwable: Throwable ->
            logError(call, throwable)
            call.respondFailure(Failure.UnhandledException(throwable))
        }
    }
}
