package app.meetacy.backend.usecase.users

import app.meetacy.backend.types.AccessIdentity
import app.meetacy.backend.types.UserId
import app.meetacy.backend.types.UserIdentity
import app.meetacy.backend.usecase.types.AuthRepository
import app.meetacy.backend.usecase.types.GetUsersViewsRepository
import app.meetacy.backend.usecase.types.UserView
import app.meetacy.backend.usecase.types.authorizeWithUserId
import app.meetacy.backend.usecase.types.getUserViewOrNull

class GetUserSafeUsecase(
    private val authRepository: AuthRepository,
    private val usersViewsRepository: GetUsersViewsRepository
) {
    suspend fun getUser(params: Params): Result {
        val ownerId = authRepository.authorizeWithUserId(params.accessIdentity) { return Result.InvalidToken }

        val userId = when (params) {
            is Params.Self -> ownerId
            is Params.User -> params.identity.userId
        }

        val user = usersViewsRepository.getUserViewOrNull(ownerId, userId)
            ?: return Result.UserNotFound

        if (params is Params.User && params.identity.accessHash != user.identity.accessHash)
            return Result.UserNotFound

        return Result.Success(user)
    }

    sealed interface Params {
        val accessIdentity: AccessIdentity
        class Self(override val accessIdentity: AccessIdentity) : Params
        class User(val identity: UserIdentity, override val accessIdentity: AccessIdentity) : Params
    }

    sealed interface Result {
        object InvalidToken : Result
        object UserNotFound : Result
        class Success(val user: UserView) : Result
    }
}
