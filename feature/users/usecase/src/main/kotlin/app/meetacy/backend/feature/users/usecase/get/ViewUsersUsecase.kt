package app.meetacy.backend.feature.users.usecase.get

import app.meetacy.backend.types.users.FullUser
import app.meetacy.backend.types.users.Relationship
import app.meetacy.backend.types.users.UserId
import app.meetacy.backend.types.users.UserView
import app.meetacy.backend.types.files.FilesRepository

class ViewUsersUsecase(
    private val filesRepository: FilesRepository,
    private val storage: Storage
) {
    suspend fun viewUsers(viewerId: UserId, users: List<FullUser>): List<UserView> {
        val avatarIds = filesRepository.getFileIdentities(
            users.mapNotNull { user -> user.avatarId }
        ).iterator()

        val userIds = users.map { userId -> userId.identity.id }

        val relationships = getRelationships(userIds, viewerId).iterator()

        return users.map { user ->
            with(user) {
                UserView(
                    isSelf = viewerId == user.identity.id,
                    relationship = relationships.next(),
                    identity = identity,
                    nickname = nickname,
                    username = username,
                    email = if (viewerId == user.identity.id) email else null,
                    emailVerified = if (viewerId == user.identity.id) emailVerified else null,
                    avatarIdentity = if (avatarId != null) avatarIds.next() else null
                )
            }
        }
    }

    /**
     * Returns relationship of two users
     * @param [viewerId] specifies ID of related user
     * @return [Relationship.Friend] if both users are subscribed on each other,
     * [Relationship.Subscription] if related user is subscribed on FullUser,
     * [Relationship.Subscriber] if FullUser is subscribed on related user,
     * null if user tries to get relationship of themselves
     */
    private suspend fun getRelationships(users: List<UserId>, viewerId: UserId): List<Relationship?> {
        val subscribers = storage.isSubscribers(
            users = users.filter { userId -> userId != viewerId }.flatMap { userId ->
                listOf(
                    IsSubscriber(
                        userId = userId,
                        subscriberId = viewerId
                    ),
                    IsSubscriber(
                        userId = viewerId,
                        subscriberId = userId
                    )
                )
            }
        ).iterator()

        return users.map { userId ->
            if (userId == viewerId) return@map null
            val isSubscriber = subscribers.next()
            val isSubscribed = subscribers.next()
            when {
                isSubscribed && isSubscriber -> Relationship.Friend
                isSubscribed -> Relationship.Subscription
                isSubscriber -> Relationship.Subscriber
                else -> Relationship.None
            }
        }
    }

    interface Storage {
        suspend fun isSubscribers(users: List<IsSubscriber>): List<Boolean>
    }

    data class IsSubscriber(
        val userId: UserId,
        val subscriberId: UserId
    )
}
