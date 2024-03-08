package app.meetacy.backend.feature.friends.endpoints.location.push

import app.meetacy.backend.core.endpoints.accessIdentity
import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.feature.friends.endpoints.location.push.PushLocationRepository.Result
import app.meetacy.backend.types.serializable.access.AccessIdentity
import app.meetacy.backend.types.serializable.location.Location
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class PushLocationBody(val location: Location)

interface PushLocationRepository {
    suspend fun push(
        accessIdentity: AccessIdentity,
        location: Location
    ): Result

    sealed interface Result {
        data object TokenInvalid : Result
        data object Success : Result
    }
}

fun Route.pushLocation(repository: PushLocationRepository) = post("/push") {
    val accessIdentity = call.accessIdentity()
    val body = call.receive<PushLocationBody>()

    when (
        repository.push(accessIdentity, body.location)
    ) {
        Result.Success -> call.respondSuccess()
        Result.TokenInvalid -> call.respondFailure(Failure.InvalidToken)
    }
}
