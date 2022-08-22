package app.meetacy.backend.mock.integration.friends

import app.meetacy.backend.mock.storage.FriendsStorage
import app.meetacy.backend.types.UserId
import app.meetacy.backend.usecase.friends.get.GetFriendsUsecase

object MockGetFriendsStorage : GetFriendsUsecase.Storage {
    override suspend fun getSubscriptions(userId: UserId): List<UserId> =
        FriendsStorage
            .getSubscriptions(userId)

    override suspend fun isSubscribed(userId: UserId, friendId: UserId): Boolean =
        FriendsStorage.isSubscribed(userId, friendId)
}
