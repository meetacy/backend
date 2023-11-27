package app.meetacy.backend.endpoint.ktor.exceptions

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException

fun interface ExceptionsHandler {
    suspend fun handle(call: ApplicationCall, throwable: Throwable)
}

// fixme: убрать костыль с вебсокетом когда
//  выйдет новый релиз rsocket. Возникающая ошибка –
//  проблема rsocket по информации от его разработчика (@why_oleg)
private val websocketsUris = listOf(
    "/auth/telegram/await",
    "/updates/stream"
)

fun Application.installExceptionsHandler(handler: ExceptionsHandler) {
    install(StatusPages) {
        exception { call, throwable: Throwable ->
            if (throwable is BadRequestException) return@exception
            if (call.request.uri in websocketsUris && throwable is ClosedReceiveChannelException) {
                call.respond(HttpStatusCode.OK)
                return@exception
            }
            call.respondFailure(Failure.UnhandledException)
            handler.handle(call, throwable)
        }
        exception { call, cause: BadRequestException ->
            val response = Failure.BadRequestException(
                message = (cause.cause ?: cause).message ?: ""
            )
            call.respondFailure(response)
        }
    }
}
