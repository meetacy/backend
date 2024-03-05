package app.meetacy.backend.feature.users.usecase.get

import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.types.auth.authorizeWithUserId
import app.meetacy.backend.types.users.*

class GetUserSafeUsecase(
    private val storage: Storage,
    private val authRepository: AuthRepository,
    private val usersViewsRepository: GetUsersViewsRepository,
) {
    suspend fun getUser(params: Params): Result {
        val ownerId = authRepository.authorizeWithUserId(params.accessIdentity) { return Result.InvalidToken }

        val userId = when (params) {
            is Params.Self -> ownerId
            is Params.User -> params.identity.id
        }

        val user = usersViewsRepository.getUserViewOrNull(ownerId, userId)
            ?: return Result.UserNotFound

        if (params is Params.User && params.identity.accessHash != user.identity.accessHash)
            return Result.UserNotFound

        val userDetails = UserDetails(
            isSelf = user.isSelf,
            relationship = user.relationship,
            identity = user.identity,
            nickname = user.nickname,
            username = user.username,
            email = user.email,
            emailVerified = user.emailVerified,
            avatarIdentity = user.avatarIdentity,
            subscribersAmount = storage.getSubscribers(userId),
            subscriptionsAmount = storage.getSubscriptions(userId),
        )

        return Result.Success(userDetails)
    }

    sealed interface Params {
        val accessIdentity: AccessIdentity
        class Self(override val accessIdentity: AccessIdentity) : Params
        class User(val identity: UserIdentity, override val accessIdentity: AccessIdentity) : Params
    }

    sealed interface Result {
        data object InvalidToken : Result
        data object UserNotFound : Result
        data class Success(val user: UserDetails) : Result
    }

    interface Storage {
        suspend fun getSubscribers(userId: UserId): Amount.OrZero
        suspend fun getSubscriptions(userId: UserId): Amount.OrZero
    }
}
