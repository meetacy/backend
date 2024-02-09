package app.meetacy.backend.endpoint.ktor.exceptions

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.rsocket.kotlin.RSocketError.ConnectionError
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.serialization.SerializationException
import java.io.IOException

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
                is ClosedReceiveChannelException,
                is ConnectionError -> {

                }
                else -> {
                    if (cause is IOException && cause.message == "Broken pipe") {
                        call.respond(HttpStatusCode.OK)
                        return@exception
                    }
                    call.respondFailure(Failure.UnhandledException)
                    handler.handle(call, cause)
                }
            }
        }
    }
}
