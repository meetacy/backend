package app.meetacy.backend.feature.friends.usecase.integration.delete

import app.meetacy.backend.feature.friends.endpoints.delete.DeleteFriendParams
import app.meetacy.backend.feature.friends.endpoints.delete.DeleteFriendRepository
import app.meetacy.backend.feature.friends.endpoints.delete.DeleteFriendResult
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.users.type
import app.meetacy.backend.feature.friends.usecase.friends.delete.DeleteFriendUsecase

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
