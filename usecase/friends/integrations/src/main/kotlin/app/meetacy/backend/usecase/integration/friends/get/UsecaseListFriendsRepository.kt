package app.meetacy.backend.usecase.integration.friends.get

import app.meetacy.backend.endpoint.friends.list.ListFriendsBody
import app.meetacy.backend.endpoint.friends.list.ListFriendsRepository
import app.meetacy.backend.endpoint.friends.list.ListFriendsResult
import app.meetacy.backend.usecase.friends.list.ListFriendsUsecase
import app.meetacy.backend.usecase.integration.types.mapToEndpoint

class UsecaseListFriendsRepository(
    private val usecase: ListFriendsUsecase
): ListFriendsRepository {
    override suspend fun getFriends(token: ListFriendsBody): ListFriendsResult =
        when (
            val result = usecase.getFriendsUsecase(
                accessIdentity = token.token.type(),
                amount = token.amount.type(),
                pagingId = token.pagingId?.type()
            )
        ) {
            ListFriendsUsecase.Result.InvalidToken ->
                ListFriendsResult.InvalidIdentity
            is ListFriendsUsecase.Result.Success ->
                ListFriendsResult.Success(
                    result.paging.map { users ->
                        users.map { it.mapToEndpoint() }
                    }
                )
        }
}
