package app.meetacy.backend.feature.users.endpoints.username.available

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.types.serializable.users.Username
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class UsernameAvailableParams(
    val username: String
)

interface UsernameAvailableRepository {
    suspend fun usernameAvailable(username: String): UsernameAvailableResult
}

sealed interface UsernameAvailableResult {
    class Success(val username: Username) : UsernameAvailableResult
    data object AlreadyOccupiedUsername : UsernameAvailableResult
    data object InvalidUsernameString : UsernameAvailableResult
}

fun Route.usernameAvailable(repository: UsernameAvailableRepository) = post("/available") {
    val params = call.receive<UsernameAvailableParams>()

    when (val result = repository.usernameAvailable(params.username)) {
        UsernameAvailableResult.InvalidUsernameString -> call.respondFailure(Failure.InvalidUtf8String)
        UsernameAvailableResult.AlreadyOccupiedUsername -> call.respondFailure(Failure.UsernameAlreadyOccupied)
        is UsernameAvailableResult.Success -> call.respondSuccess(result.username)
    }
}
