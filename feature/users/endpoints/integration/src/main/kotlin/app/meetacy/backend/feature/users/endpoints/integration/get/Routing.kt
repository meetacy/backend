package app.meetacy.backend.feature.users.endpoints.integration.get

import app.meetacy.backend.feature.users.endpoints.get.GetUserResult
import app.meetacy.backend.feature.users.endpoints.get.UserRepository
import app.meetacy.backend.feature.users.endpoints.get.getUser
import app.meetacy.backend.feature.users.usecase.get.GetUserSafeUsecase
import app.meetacy.backend.types.serializable.access.AccessIdentity
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.users.UserIdentity
import app.meetacy.backend.types.serializable.users.serializable
import app.meetacy.backend.types.serializable.users.type
import app.meetacy.di.DI
import io.ktor.server.routing.*

internal fun Route.getUser(di: DI) {
    val usecase: GetUserSafeUsecase by di.getting
    val repository = object : UserRepository {
        override suspend fun getUser(identity: UserIdentity?, token: AccessIdentity): GetUserResult =
            when (val result = usecase.getUser(toUsecase(identity, token))) {
                GetUserSafeUsecase.Result.InvalidToken -> GetUserResult.InvalidIdentity
                GetUserSafeUsecase.Result.UserNotFound -> GetUserResult.UserNotFound
                is GetUserSafeUsecase.Result.Success -> GetUserResult.Success(result.user.serializable())
            }

        private fun toUsecase(id: UserIdentity?, token: AccessIdentity) = when {
            id != null -> GetUserSafeUsecase.Params.User(id.type(), token.type())
            else -> GetUserSafeUsecase.Params.Self(token.type())
        }
    }

    getUser(repository)
}
