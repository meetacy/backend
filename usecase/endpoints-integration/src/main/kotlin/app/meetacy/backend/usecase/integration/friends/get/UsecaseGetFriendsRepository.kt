package app.meetacy.backend.usecase.integration.friends.get

import app.meetacy.backend.endpoint.friends.get.GetFriendsRepository
import app.meetacy.backend.endpoint.friends.get.GetFriendsResult
import app.meetacy.backend.endpoint.friends.get.GetFriendsToken
import app.meetacy.backend.usecase.friends.get.GetFriendsUsecase
import app.meetacy.backend.usecase.integration.types.mapToEndpoint

class UsecaseGetFriendsRepository(
    private val usecase: GetFriendsUsecase
): GetFriendsRepository {
    override suspend fun getFriends(token: GetFriendsToken): GetFriendsResult =
        when (val result = usecase.getFriendsUsecase(token.accessIdentity.type())) {
            GetFriendsUsecase.Result.InvalidToken ->
                GetFriendsResult.InvalidToken
            is GetFriendsUsecase.Result.Success ->
                GetFriendsResult.Success(
                    result.friends.map { it.mapToEndpoint() },
                    result.subscriptions.map { it.mapToEndpoint() }
                )
        }
}
