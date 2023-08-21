package app.meetacy.backend.feature.friends.endpoints.integration.add

import app.meetacy.backend.feature.friends.endpoints.add.AddFriendParams
import app.meetacy.backend.feature.friends.endpoints.add.AddFriendRepository
import app.meetacy.backend.feature.friends.endpoints.add.AddFriendResult
import app.meetacy.backend.feature.friends.endpoints.add.addFriend
import app.meetacy.backend.feature.friends.usecase.add.AddFriendUsecase
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.users.type
import app.meetacy.di.global.di
import io.ktor.server.routing.*

fun Route.addFriend() {
    val usecase: AddFriendUsecase by di.getting
    val repository = object : AddFriendRepository {
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
    addFriend(repository)
}
