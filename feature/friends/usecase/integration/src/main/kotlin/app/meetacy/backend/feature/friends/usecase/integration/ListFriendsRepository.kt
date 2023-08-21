package app.meetacy.backend.feature.friends.usecase.integration

import app.meetacy.backend.feature.friends.database.friends.FriendsStorage
import app.meetacy.backend.feature.friends.usecase.list.ListFriendsUsecase
import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.types.paging.PagingId
import app.meetacy.backend.types.paging.PagingResult
import app.meetacy.backend.types.users.GetUsersViewsRepository
import app.meetacy.backend.types.users.UserId
import app.meetacy.di.builder.DIBuilder

fun DIBuilder.listFriendsUsecase() {
    val listFriendsUsecase by singleton<ListFriendsUsecase> {
        val authRepository: AuthRepository by getting
        val getUsersViewsRepository: GetUsersViewsRepository by getting
        val storage = object : ListFriendsUsecase.Storage {
            private val friendsStorage: FriendsStorage by getting

            override suspend fun getFriends(
                userId: UserId,
                amount: Amount,
                pagingId: PagingId?
            ): PagingResult<UserId> = friendsStorage.getFriends(userId, amount, pagingId)
        }

        ListFriendsUsecase(authRepository, storage, getUsersViewsRepository)
    }
}
