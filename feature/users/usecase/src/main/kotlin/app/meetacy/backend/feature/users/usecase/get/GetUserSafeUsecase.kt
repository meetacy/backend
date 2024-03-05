package app.meetacy.backend.feature.users.usecase.get

import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.types.auth.authorizeWithUserId
import app.meetacy.backend.types.users.*

class GetUserSafeUsecase(
    private val storage: Storage,
    private val authRepository: AuthRepository,
    private val getUsersViewsRepository: GetUsersViewsRepository,
    private val viewUsersRepository: ViewUsersRepository
) {
    suspend fun getUser(identifier: Identifier): Result {
        val viewerId = authRepository.authorizeWithUserId(identifier.accessIdentity) {
            return Result.InvalidToken
        }

        val userId = when (identifier) {
            is Identifier.Self -> viewerId
            is Identifier.ByUserId -> identifier.identity.id
            is Identifier.ByUsername -> return getUser(identifier, viewerId)
        }

        val user = getUsersViewsRepository.getUserViewOrNull(viewerId, userId)
            ?: return Result.UserNotFound

        if (identifier is Identifier.ByUserId) {
            val expectedHash = identifier.identity.accessHash
            val actualHash = user.identity.accessHash
            if (expectedHash != actualHash) return Result.UserNotFound
        }

        val details = getUserDetails(user)
        return Result.Success(details)
    }

    private suspend fun getUser(
        identifier: Identifier.ByUsername,
        viewerId: UserId
    ): Result {
        val fullUser = storage.getUserByUsername(identifier.username) ?: return Result.UserNotFound
        val userView = viewUsersRepository.viewUsers(viewerId, listOf(fullUser)).first()
        val details = getUserDetails(userView)
        return Result.Success(details)
    }

    private suspend fun getUserDetails(user: UserView): UserDetails {
        return UserDetails(
            isSelf = user.isSelf,
            relationship = user.relationship,
            identity = user.identity,
            nickname = user.nickname,
            username = user.username,
            email = user.email,
            emailVerified = user.emailVerified,
            avatarIdentity = user.avatarIdentity,
            subscribersAmount = storage.getSubscribers(user.identity.id),
            subscriptionsAmount = storage.getSubscriptions(user.identity.id),
        )
    }

    sealed interface Identifier {
        val accessIdentity: AccessIdentity
        data class Self(override val accessIdentity: AccessIdentity) : Identifier
        data class ByUserId(val identity: UserIdentity, override val accessIdentity: AccessIdentity) : Identifier
        data class ByUsername(val username: Username, override val accessIdentity: AccessIdentity) : Identifier
    }

    sealed interface Result {
        data object InvalidToken : Result
        data object UserNotFound : Result
        data class Success(val user: UserDetails) : Result
    }

    interface Storage {
        suspend fun getUserByUsername(username: Username): FullUser?
        suspend fun getSubscribers(userId: UserId): Amount.OrZero
        suspend fun getSubscriptions(userId: UserId): Amount.OrZero
    }
}
