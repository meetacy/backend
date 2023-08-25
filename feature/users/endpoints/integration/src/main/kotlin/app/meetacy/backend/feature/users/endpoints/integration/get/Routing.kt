package app.meetacy.backend.feature.users.endpoints.integration.get

import app.meetacy.backend.feature.users.endpoints.get.GetUserParams
import app.meetacy.backend.feature.users.endpoints.get.GetUserResult
import app.meetacy.backend.feature.users.endpoints.get.UserRepository
import app.meetacy.backend.feature.users.endpoints.get.getUser
import app.meetacy.backend.feature.users.usecase.get.GetUserSafeUsecase
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.users.serializable
import app.meetacy.backend.types.serializable.users.type
import app.meetacy.di.global.di
import io.ktor.server.routing.*

internal fun Route.getUser() {
    val usecase: GetUserSafeUsecase by di.getting
    val repository = object : UserRepository {
        override suspend fun getUser(params: GetUserParams): GetUserResult =
            when (val result = usecase.getUser(params.toUsecase())) {
                GetUserSafeUsecase.Result.InvalidToken -> GetUserResult.InvalidIdentity
                GetUserSafeUsecase.Result.UserNotFound -> GetUserResult.UserNotFound
                is GetUserSafeUsecase.Result.Success -> GetUserResult.Success(result.user.serializable())
            }

        private fun GetUserParams.toUsecase() = when {
            id != null -> GetUserSafeUsecase.Params.User(id!!.type(), token.type())
            else -> GetUserSafeUsecase.Params.Self(token.type())
        }
    }

    getUser(repository)
}
