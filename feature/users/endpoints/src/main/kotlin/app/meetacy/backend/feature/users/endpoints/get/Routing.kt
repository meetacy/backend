package app.meetacy.backend.feature.users.endpoints.get

import app.meetacy.backend.core.endpoints.accessIdentity
import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.types.serializable.access.AccessIdentity
import app.meetacy.backend.types.serializable.users.User
import app.meetacy.backend.types.serializable.users.UserIdentity
import com.typesafe.config.Optional
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

interface UserRepository {
    suspend fun getUser(id: UserIdentity?, token: AccessIdentity): GetUserResult
}

@Serializable
@Optional
data class GetUserParams(
    val id: UserIdentity? = null
)

sealed interface GetUserResult {
    data object InvalidIdentity : GetUserResult
    data object UserNotFound : GetUserResult
    class Success(val user: User) : GetUserResult
}

fun Route.getUser(provider: UserRepository) = post("/get") {
    val id = call.receive<GetUserParams>().id
    val token = call.accessIdentity()
    when (val result = provider.getUser(id, token)) {
        is GetUserResult.Success -> call.respondSuccess(result.user)
        GetUserResult.UserNotFound -> call.respondFailure(Failure.UserNotFound)
        GetUserResult.InvalidIdentity -> call.respondFailure(Failure.InvalidToken)
    }
}
