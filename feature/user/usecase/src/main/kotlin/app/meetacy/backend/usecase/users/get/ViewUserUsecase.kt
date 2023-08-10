package app.meetacy.backend.usecase.users.get

import app.meetacy.backend.types.users.Relationship
import app.meetacy.backend.types.users.UserId
import app.meetacy.backend.usecase.types.FilesRepository
import app.meetacy.backend.usecase.types.FullUser
import app.meetacy.backend.usecase.types.UserView

class ViewUserUsecase(
    private val filesRepository: FilesRepository,
    private val storage: Storage
) {
    suspend fun viewUsers(viewerId: UserId, users: List<FullUser>): List<UserView> {
        val avatarIds = filesRepository.getFileIdentities(
            users.mapNotNull { user -> user.avatarId }
        ).iterator()

        return users.map { user ->
            with(user) {
                UserView(
                    isSelf = viewerId == user.identity.id,
                    relationship = getRelationship(viewerId),
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

    suspend fun viewUser(viewerId: UserId, user: FullUser) = viewUsers(viewerId, listOf(user)).first()

    /**
     * Returns relationship of 2 users
     * @param [userId] specifies ID of related user
     * @return [Relationship.Friend] if both users are subscribed on each other,
     * [Relationship.Subscribed] if related user is subscribed on FullUser,
     * [Relationship.Subscriber] if FullUser is subscribed on related user,
     * null if user tries to get relationship of themselves
     */
    private suspend fun FullUser.getRelationship(userId: UserId): Relationship? {
        if (identity.id == userId) return null
        val isSubscriber = storage.isSubscriber(identity.id, userId)
        val isSubscribed = storage.isSubscriber(userId, identity.id)
        return when {
            isSubscribed && isSubscriber -> Relationship.Friend
            isSubscribed -> Relationship.Subscribed
            isSubscriber -> Relationship.Subscriber
            else -> Relationship.None
        }
    }

    interface Storage {
        suspend fun isSubscriber(userId: UserId, subscriberId: UserId): Boolean
    }
}
