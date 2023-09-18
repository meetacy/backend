package app.meetacy.backend.endpoint.ktor.exceptions

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.utils.io.*

fun Application.installExceptionsHandler() {
    install(StatusPages) {
        exception { call: ApplicationCall, throwable: Throwable ->
            logError(call, throwable)
            throwable.printStack()
            call.respondFailure(Failure.UnhandledException(throwable))
        }
    }
}
