package app.meetacy.backend.usecase.integration.friends.add

import app.meetacy.backend.endpoint.friends.add.AddFriendParams
import app.meetacy.backend.endpoint.friends.add.AddFriendRepository
import app.meetacy.backend.endpoint.friends.add.AddFriendResult
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.user.type
import app.meetacy.backend.usecase.friends.add.AddFriendUsecase

class UsecaseAddFriendRepository(
    private val usecase: AddFriendUsecase
) : AddFriendRepository {
    override suspend fun addFriend(addFriendParams: AddFriendParams): AddFriendResult = with(addFriendParams) {
        when (usecase.addFriendUsecase(token.type(), friendId.type())) {
            AddFriendUsecase.Result.FriendNotFound ->
                AddFriendResult.FriendNotFound
            AddFriendUsecase.Result.InvalidToken ->
                AddFriendResult.InvalidIdentity
            AddFriendUsecase.Result.Success ->
                AddFriendResult.Success
            AddFriendUsecase.Result.FriendAlreadyAdded ->
                AddFriendResult.FriendAlreadyAdded
        }
    }
}
