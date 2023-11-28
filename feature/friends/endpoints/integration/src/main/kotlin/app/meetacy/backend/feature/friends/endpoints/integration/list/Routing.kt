package app.meetacy.backend.feature.friends.endpoints.integration.list

import app.meetacy.backend.feature.friends.endpoints.list.ListFriendsRepository
import app.meetacy.backend.feature.friends.endpoints.list.ListFriendsResult
import app.meetacy.backend.feature.friends.endpoints.list.listFriends
import app.meetacy.backend.feature.friends.usecase.list.ListFriendsUsecase
import app.meetacy.backend.types.paging.serializable.PagingId
import app.meetacy.backend.types.paging.serializable.serializable
import app.meetacy.backend.types.paging.serializable.type
import app.meetacy.backend.types.serializable.access.AccessIdentity
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.amount.Amount
import app.meetacy.backend.types.serializable.amount.type
import app.meetacy.backend.types.serializable.users.serializable
import app.meetacy.backend.types.users.UserView
import app.meetacy.di.DI
import io.ktor.server.routing.*

fun Route.listFriends(di: DI) {
    val usecase: ListFriendsUsecase by di.getting
    val repository = object : ListFriendsRepository {
        override suspend fun getFriends(token: AccessIdentity, amount: Amount, pagingId: PagingId?): ListFriendsResult =
            when (
                val result = usecase.getFriendsUsecase(
                    accessIdentity = token.type(),
                    amount = amount.type(),
                    pagingId = pagingId?.type()
                )
            ) {
                ListFriendsUsecase.Result.InvalidToken ->
                    ListFriendsResult.InvalidIdentity
                is ListFriendsUsecase.Result.Success ->
                    ListFriendsResult.Success(
                        result.paging.map { users ->
                            users.map(UserView::serializable)
                        }.serializable()
                    )
            }
    }
    listFriends(repository)
}
