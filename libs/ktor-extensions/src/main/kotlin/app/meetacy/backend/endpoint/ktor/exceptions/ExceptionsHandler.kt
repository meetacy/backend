package app.meetacy.backend.endpoint.ktor.exceptions

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.statuspages.*

fun interface ExceptionsHandler {
    suspend fun handle(call: ApplicationCall, throwable: Throwable)
}

fun Application.installExceptionsHandler(handler: ExceptionsHandler) {
    install(StatusPages) {
        exception { call, cause: BadRequestException ->
            val response = Failure.BadRequestException(
                message = (cause.cause ?: cause).message ?: ""
            )
            call.respondFailure(response)
        }
        exception { call, throwable: Throwable ->
            if (throwable is BadRequestException) return@exception
            call.respondFailure(Failure.UnhandledException)
            handler.handle(call, throwable)
        }
    }
}
