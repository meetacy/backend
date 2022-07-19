package app.meetacy.backend.usecase.users

import app.meetacy.backend.domain.AccessHash
import app.meetacy.backend.domain.AccessToken
import app.meetacy.backend.domain.UserId
import app.meetacy.backend.usecase.types.UserView

class GetUserSafeUsecase(
    private val storage: Storage,
    private val usersViewsUsecase: GetUsersViewsUsecase
) {
    suspend fun getUser(params: Params): Result {
        val ownerId = storage.getTokenOwnerId(params.accessToken)
            ?: return Result.InvalidToken

        val userId = when (params) {
            is Params.Self -> ownerId
            is Params.User -> params.id
        }

        val user = usersViewsUsecase.viewUsers(ownerId, listOf(userId)).first()
            ?: return Result.UserNotFound

        if (params is Params.User && params.accessHash != user.accessHash)
            return Result.UserNotFound

        return Result.Success(user)
    }

    interface Storage {
        fun getTokenOwnerId(token: AccessToken): UserId?
    }

    sealed interface Params {
        val accessToken: AccessToken
        class Self(override val accessToken: AccessToken) : Params
        class User(val id: UserId, val accessHash: AccessHash, override val accessToken: AccessToken) : Params
    }

    sealed interface Result {
        object InvalidToken : Result
        object UserNotFound : Result
        class Success(val user: UserView) : Result
    }
}
