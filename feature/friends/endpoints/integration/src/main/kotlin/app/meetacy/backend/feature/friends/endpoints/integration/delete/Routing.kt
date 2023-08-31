package app.meetacy.backend.feature.friends.endpoints.integration.delete

import app.meetacy.backend.feature.friends.endpoints.delete.DeleteFriendParams
import app.meetacy.backend.feature.friends.endpoints.delete.DeleteFriendRepository
import app.meetacy.backend.feature.friends.endpoints.delete.DeleteFriendResult
import app.meetacy.backend.feature.friends.endpoints.delete.deleteFriend
import app.meetacy.backend.feature.friends.usecase.delete.DeleteFriendUsecase
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.users.type
import app.meetacy.di.DI
import io.ktor.server.routing.*

fun Route.deleteFriend(di: DI) {
    val usecase: DeleteFriendUsecase by di.getting
    val repository = object : DeleteFriendRepository {
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
    deleteFriend(repository)
}
