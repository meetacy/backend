package app.meetacy.backend.mock.integration.friends

import app.meetacy.backend.mock.storage.FriendsStorage
import app.meetacy.backend.types.FriendsAndSubscriptions
import app.meetacy.backend.types.UserId
import app.meetacy.backend.usecase.friends.get.GetFriendsUsecase

object MockGetFriendsStorage : GetFriendsUsecase.Storage {
    override fun getFriendsAndSubscriptions(userId: UserId): FriendsAndSubscriptions =
        FriendsStorage
            .friendsAndSubscriptions(userId)
}
