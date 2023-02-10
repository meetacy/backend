package app.meetacy.backend.endpoint.ktor

import io.ktor.server.application.*
import io.ktor.server.response.*
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonPrimitive

suspend inline fun <reified T> ApplicationCall.respondSuccess(result: T) {
    respond(ServerResponse.Success(true, result))
}

suspend fun ApplicationCall.respondEmptySuccess() {
    this@respondEmptySuccess.respondSuccess<JsonPrimitive>(JsonNull)
}

suspend fun ApplicationCall.respondFailure(errorCode: Int, errorMessage: String) {
    respond(ServerResponse.Failure(false, errorCode, errorMessage))
}



