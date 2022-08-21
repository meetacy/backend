package app.meetacy.backend.test

import app.meetacy.backend.endpoint.friends.add.AddFriendParams
import app.meetacy.backend.endpoint.friends.add.AddFriendRepository
import app.meetacy.backend.endpoint.friends.add.AddFriendResult

object TestAddFriendRepository : AddFriendRepository {
    override suspend fun addFriend(addFriendParams: AddFriendParams): AddFriendResult =
        AddFriendResult.FriendNotFound
}
