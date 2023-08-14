package app.meetacy.backend.feature.auth.usecase.integration.friends.delete

import app.meetacy.backend.endpoint.friends.delete.DeleteFriendParams
import app.meetacy.backend.endpoint.friends.delete.DeleteFriendRepository
import app.meetacy.backend.endpoint.friends.delete.DeleteFriendResult
import app.meetacy.backend.feature.auth.usecase.friends.delete.DeleteFriendUsecase
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.user.type

class UsecaseDeleteFriendRepository(
    private val usecase: DeleteFriendUsecase
) : DeleteFriendRepository {
    override suspend fun deleteFriend(deleteFriendParams: DeleteFriendParams): DeleteFriendResult = with(deleteFriendParams) {
        when (usecase.deleteFriendUsecase(token.type(), friendId.type())) {
            DeleteFriendUsecase.Result.FriendNotFound ->
                DeleteFriendResult.FriendNotFound
            DeleteFriendUsecase.Result.InvalidToken ->
                DeleteFriendResult.InvalidIdentity
            DeleteFriendUsecase.Result.Success ->
                DeleteFriendResult.Success
        }
    }
}
