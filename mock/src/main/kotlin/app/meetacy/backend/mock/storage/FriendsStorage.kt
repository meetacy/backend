package app.meetacy.backend.mock.storage

import app.meetacy.backend.types.UserId

object FriendsStorage {
    private val data: MutableList<MockFriend> = mutableListOf()

    fun addFriend(userId: UserId, friendId: UserId) {
        data.add(
            MockFriend(
                userId , friendId
            )
        )
    }

    fun isSubscribed(userId: UserId, friendId: UserId): Boolean = data
        .any { friend -> friend.userId == userId && friend.friendId == friendId }

    fun getSubscriptions(userId: UserId): List<UserId> = data
        .filter { it.userId == userId }
        .map { it.friendId }
}
