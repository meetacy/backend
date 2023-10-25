package app.meetacy.backend.application.endpoints

import io.ktor.server.application.*
import app.meetacy.backend.endpoint.ktor.exceptions.ExceptionsHandler as KtorExceptionsHandler

fun interface ExceptionsHandler {
    suspend fun handle(call: ApplicationCall, throwable: Throwable)

    object Simple : ExceptionsHandler {
        override suspend fun handle(call: ApplicationCall, throwable: Throwable) {
            throwable.printStackTrace()
        }
    }
}

internal fun ExceptionsHandler.map(): KtorExceptionsHandler =
    KtorExceptionsHandler(this::handle)
