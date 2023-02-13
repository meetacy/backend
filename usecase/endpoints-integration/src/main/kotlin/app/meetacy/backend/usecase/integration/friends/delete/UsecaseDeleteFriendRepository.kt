package app.meetacy.backend.usecase.integration.friends.delete

import app.meetacy.backend.endpoint.friends.delete.DeleteFriendParams
import app.meetacy.backend.endpoint.friends.delete.DeleteFriendRepository
import app.meetacy.backend.endpoint.friends.delete.DeleteFriendResult
import app.meetacy.backend.usecase.friends.delete.DeleteFriendUsecase

class UsecaseDeleteFriendRepository(
    private val usecase: DeleteFriendUsecase
) : DeleteFriendRepository {
    override suspend fun deleteFriend(deleteFriendParams: DeleteFriendParams): DeleteFriendResult = with(deleteFriendParams) {
        when (usecase.deleteFriendUsecase(accessIdentity.type(), friendIdentity.type())) {
            DeleteFriendUsecase.Result.FriendNotFound ->
                DeleteFriendResult.FriendNotFound
            DeleteFriendUsecase.Result.InvalidToken ->
                DeleteFriendResult.InvalidIdentity
            DeleteFriendUsecase.Result.Success ->
                DeleteFriendResult.Success
        }
    }
}
