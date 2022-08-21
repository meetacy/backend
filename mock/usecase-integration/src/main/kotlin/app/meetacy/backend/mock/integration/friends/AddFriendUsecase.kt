package app.meetacy.backend.mock.integration.friends

import app.meetacy.backend.mock.storage.FriendsStorage
import app.meetacy.backend.types.UserId
import app.meetacy.backend.usecase.friends.add.AddFriendUsecase

object MockAddFriendStorage : AddFriendUsecase.Storage {
    override suspend fun addFriend(userId: UserId, friendId: UserId) =
        FriendsStorage
            .addFriend(userId, friendId)
}
