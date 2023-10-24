package app.meetacy.backend.feature.friends.endpoints.integration.add

import app.meetacy.backend.feature.friends.endpoints.add.AddFriendRepository
import app.meetacy.backend.feature.friends.endpoints.add.AddFriendResult
import app.meetacy.backend.feature.friends.endpoints.add.addFriend
import app.meetacy.backend.feature.friends.usecase.add.AddFriendUsecase
import app.meetacy.backend.types.serializable.access.AccessIdentity
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.users.UserIdentity
import app.meetacy.backend.types.serializable.users.type
import app.meetacy.di.DI
import io.ktor.server.routing.*

fun Route.addFriend(di: DI) {
    val usecase: AddFriendUsecase by di.getting
    val repository = object : AddFriendRepository {
        override suspend fun addFriend(token: AccessIdentity, friendId: UserIdentity): AddFriendResult {
            return when (usecase.addFriendUsecase(token.type(), friendId.type())) {
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
