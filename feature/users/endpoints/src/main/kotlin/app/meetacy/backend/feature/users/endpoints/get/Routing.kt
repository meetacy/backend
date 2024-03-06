package app.meetacy.backend.feature.users.endpoints.get

import app.meetacy.backend.core.endpoints.accessIdentity
import app.meetacy.backend.core.endpoints.userIdOrNull
import app.meetacy.backend.core.endpoints.usernameOrNull
import app.meetacy.backend.endpoint.ktor.Failure
import app.meetacy.backend.endpoint.ktor.respondFailure
import app.meetacy.backend.endpoint.ktor.respondSuccess
import app.meetacy.backend.types.serializable.access.AccessIdentity
import app.meetacy.backend.types.serializable.users.UserDetails
import app.meetacy.backend.types.serializable.users.UserId
import app.meetacy.backend.types.serializable.users.Username
import io.ktor.server.application.*
import io.ktor.server.routing.*

interface UserRepository {
    suspend fun getUser(identifier: Identifier): GetUserResult

    sealed interface Identifier {
        val accessIdentity: AccessIdentity

        data class Self(
            override val accessIdentity: AccessIdentity
        ) : Identifier
        data class ByUserId(
            val userId: UserId,
            override val accessIdentity: AccessIdentity,
        ) : Identifier
        data class ByUsername(
            val username: Username,
            override val accessIdentity: AccessIdentity,
        ) : Identifier
    }
}

sealed interface GetUserResult {
    data object InvalidIdentity : GetUserResult
    data object UserNotFound : GetUserResult
    data class Success(val user: UserDetails) : GetUserResult
}

fun Route.getUser(provider: UserRepository) = get("/get") {
    val id = call.parameters.userIdOrNull(name = "id")
    val username = call.parameters.usernameOrNull()
    val token = call.accessIdentity()

    val identifier = when {
        id != null -> UserRepository.Identifier.ByUserId(id, token)
        username != null -> UserRepository.Identifier.ByUsername(username, token)
        else -> UserRepository.Identifier.Self(token)
    }

    when (val result = provider.getUser(identifier)) {
        is GetUserResult.Success -> call.respondSuccess(result.user)
        GetUserResult.UserNotFound -> call.respondFailure(Failure.UserNotFound)
        GetUserResult.InvalidIdentity -> call.respondFailure(Failure.InvalidToken)
    }
}
