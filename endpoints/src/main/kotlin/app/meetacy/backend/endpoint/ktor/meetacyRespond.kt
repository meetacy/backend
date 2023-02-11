package app.meetacy.backend.endpoint.ktor

import io.ktor.server.application.*
import io.ktor.server.response.*

suspend inline fun <reified T> ApplicationCall.respondSuccess(result: T) {
    respond(Success(true, result))
}

suspend fun ApplicationCall.respondSuccess() {
    respond(EmptySuccess(true))
}

suspend fun ApplicationCall.respondFailure(errorCode: Int, errorMessage: String) {
    respond(Failure(false, errorCode, errorMessage))
}



