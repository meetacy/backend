package app.meetacy.backend.feature.friends.usecase.subscriptions.list

import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.types.auth.authorizeWithUserId
import app.meetacy.backend.types.paging.PagingId
import app.meetacy.backend.types.paging.PagingResult
import app.meetacy.backend.types.users.*

class ListSubscriptionsUsecase(
    private val storage: Storage,
    private val authRepository: AuthRepository,
    private val getUsersViewsRepository: GetUsersViewsRepository
) {
    suspend fun listSubscriptions(identifier: Identifier): Result {
        val viewerId = authRepository.authorizeWithUserId(identifier.accessIdentity) {
            return Result.InvalidToken
        }

        val userId = when (identifier) {
            is Identifier.Self -> viewerId
            is Identifier.ByUserId -> identifier.identity.id
        }

        val user = getUsersViewsRepository.getUserViewOrNull(viewerId, userId)
            ?: return Result.UserNotFound

        if (identifier is Identifier.ByUserId) {
            val expectedHash = identifier.identity.accessHash
            val actualHash = user.identity.accessHash
            if (expectedHash != actualHash) return Result.UserNotFound
        }

        val subscriberIdsPaging = storage.getSubscriptions(userId, identifier.amount, identifier.pagingId)

        val subscribersPaging = subscriberIdsPaging.map { subscriberIds ->
            getUsersViewsRepository.getUsersViews(userId, subscriberIds)
        }


        return Result.Success(subscribersPaging)
    }

    sealed interface Identifier {
        val accessIdentity: AccessIdentity
        val amount: Amount
        val pagingId: PagingId?

        data class Self(
            override val accessIdentity: AccessIdentity,
            override val amount: Amount,
            override val pagingId: PagingId?
        ) : Identifier
        data class ByUserId(
            val identity: UserIdentity,
            override val accessIdentity: AccessIdentity,
            override val amount: Amount,
            override val pagingId: PagingId?,
        ) : Identifier
    }

    sealed interface Result {
        data object InvalidToken : Result
        data object UserNotFound : Result
        data class Success(val paging: PagingResult<UserView>) : Result
    }

    interface Storage {
        suspend fun getSubscriptions(
            userId: UserId,
            amount: Amount,
            pagingId: PagingId?
        ): PagingResult<UserId>
    }
}
