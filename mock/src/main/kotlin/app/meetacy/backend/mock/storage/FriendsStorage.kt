package app.meetacy.backend.mock.storage

import app.meetacy.backend.types.FriendsAndSubscriptions
import app.meetacy.backend.types.UserId

object FriendsStorage {
    private val data: MutableList<MockFriend> = mutableListOf()

    fun addFriend(userId: UserId, friendId: UserId) {
        if (!isSubscribed(userId, friendId) && userId != friendId){
            data.add(
                MockFriend(
                    userId , friendId
                )
            )
        }
    }

    fun isSubscribed(userId: UserId, friendId: UserId): Boolean = data
        .any { friend -> friend.userId == userId && friend.friendId == friendId }

    fun friendsAndSubscriptions(userId: UserId): FriendsAndSubscriptions = data
        // получаем все подписки
        .filter { it.userId == userId }
        // получаем взаимные подписки
        .partition { friend -> isSubscribed(friend.friendId, friend.userId) }
        .let {
            (friends, subscriptions) -> FriendsAndSubscriptions(
                friends.map { it.friendId }, subscriptions.map { it.userId }
            )
        }

    fun getSubscriptions(userId: UserId): List<UserId> = data
            .filter { it.userId == userId }
            .map { it.friendId }
}
