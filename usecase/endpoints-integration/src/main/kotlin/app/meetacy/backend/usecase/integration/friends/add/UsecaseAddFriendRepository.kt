package app.meetacy.backend.usecase.integration.friends.add

import app.meetacy.backend.endpoint.friends.add.AddFriendParams
import app.meetacy.backend.endpoint.friends.add.AddFriendRepository
import app.meetacy.backend.endpoint.friends.add.AddFriendResult
import app.meetacy.backend.usecase.friends.add.AddFriendUsecase

class UsecaseAddFriendRepository(
    private val usecase: AddFriendUsecase
) : AddFriendRepository {
    override suspend fun addFriend(addFriendParams: AddFriendParams): AddFriendResult = with(addFriendParams) {
        when (usecase.addFriendUsecase(accessToken.type(), friendIdentity.type())) {
            AddFriendUsecase.Result.FriendNotFound ->
                AddFriendResult.FriendNotFound
            AddFriendUsecase.Result.InvalidToken ->
                AddFriendResult.InvalidToken
            AddFriendUsecase.Result.Success ->
                AddFriendResult.Success
        }
    }
}
