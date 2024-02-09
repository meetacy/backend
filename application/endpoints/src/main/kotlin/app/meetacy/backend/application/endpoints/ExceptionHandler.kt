package app.meetacy.backend.application.endpoints

import io.ktor.server.application.*
import app.meetacy.backend.endpoint.ktor.exceptions.ExceptionsHandler as KtorExceptionsHandler

fun interface ExceptionHandler {
    suspend fun handle(call: ApplicationCall, throwable: Throwable)

    object Simple : ExceptionHandler {
        override suspend fun handle(call: ApplicationCall, throwable: Throwable) {
            throwable.printStackTrace()
        }
    }
}

internal fun ExceptionHandler.map(): KtorExceptionsHandler =
    KtorExceptionsHandler(this::handle)
