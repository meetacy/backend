package app.meetacy.backend.feature.users.endpoints.validate

import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.types.serializable.users.Username
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

@Serializable
data class ValidateParam(
    val username: String
)

interface ValidateUsernameRepository {
    suspend fun validateUsername(username: String): ValidateUsernameResult
}

sealed interface ValidateUsernameResult {
    class Success(val username: Username) : ValidateUsernameResult
    data object AlreadyOccupiedUsername : ValidateUsernameResult
    data object InvalidValidateUsernameString : ValidateUsernameResult
}

fun Route.validateUsername(repository: ValidateUsernameRepository) = post("/validate") {
    val validateParam = call.receive<ValidateParam>()

    when (val result = repository.validateUsername(validateParam.username)) {
        ValidateUsernameResult.InvalidValidateUsernameString -> call.respondFailure(Failure.InvalidUtf8String)
        ValidateUsernameResult.AlreadyOccupiedUsername -> call.respondFailure(Failure.UsernameAlreadyOccupied)
        is ValidateUsernameResult.Success -> call.respondSuccess(result.username)
    }
}
