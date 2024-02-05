package app.meetacy.backend.endpoint.ktor.exceptions

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.rsocket.kotlin.RSocketError
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.serialization.SerializationException

fun interface ExceptionsHandler {
    suspend fun handle(call: ApplicationCall, throwable: Throwable)
}

// fixme: убрать костыль с вебсокетом когда
//  выйдет новый релиз rsocket. Возникающая ошибка –
//  проблема rsocket по информации от его разработчика (@why_oleg)

// убрано, вроде

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
                    if (cause is ClosedReceiveChannelException || cause is RSocketError.ConnectionError) {
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
