package app.meetacy.backend.endpoint.ktor.exceptions

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.statuspages.*
import kotlinx.serialization.SerializationException

fun interface ExceptionsHandler {
    suspend fun handle(call: ApplicationCall, throwable: Throwable)
}

fun Application.installExceptionsHandler(handler: ExceptionsHandler) {
    install(StatusPages) {
        exception { call, cause: Throwable ->
            when (cause) {
                is BadRequestException,
                is SerializationException -> {
                    val response = Failure.BadRequest(
                        message = (cause.cause ?: cause).message ?: ""
                    )
                    call.respondFailure(response)
                }
                else -> {
                    call.respondFailure(Failure.UnhandledException)
                    handler.handle(call, cause)
                }
            }
        }
    }
}
