package app.meetacy.backend.usecase.users.get

import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.types.FilesRepository
import app.meetacy.backend.usecase.types.FullUser
import app.meetacy.backend.usecase.types.UserView

class ViewUserUsecase(
    private val filesRepository: FilesRepository,
    private val isSubscriberStorage: IsSubscriberStorage
) {
    suspend fun viewUsers(viewerId: UserId, users: List<FullUser>): List<UserView> {
        val avatarIds = filesRepository.getFileIdentities(
            users.mapNotNull { user -> user.avatarId }
        ).iterator()

        return users.map { user ->
            with(user) {
                val isSelf = viewerId == user.identity.userId
                val friendship: UserView.Friendship? = let {
                    if (isSelf)
                        return@let null
                    val viewerIsSubscriber = isSubscriberStorage.isSubscriber(viewerId = viewerId, expectedSubscriberId = user.identity.userId)
                    val userIsSubscriber = isSubscriberStorage.isSubscriber(viewerId = user.identity.userId, expectedSubscriberId = viewerId)

                    if (viewerIsSubscriber)
                        if (userIsSubscriber)
                            UserView.Friendship.Friends
                        else
                            UserView.Friendship.Subscription
                    else
                        if (userIsSubscriber)
                            UserView.Friendship.Subscriber
                        else
                            UserView.Friendship.None
                }

                UserView(
                    isSelf = isSelf,
                    friendship = friendship,
                    identity = identity,
                    gender = gender,
                    nickname = nickname,
                    email = if (viewerId == user.identity.userId) email else null,
                    emailVerified = if (viewerId == user.identity.userId) emailVerified else null,
                    avatarIdentity = if (avatarId != null) avatarIds.next() else null
                )
            }
        }
    }

    suspend fun viewUser(viewerId: UserId, user: FullUser) = viewUsers(viewerId, listOf(user)).first()

    interface IsSubscriberStorage {
        suspend fun isSubscriber(viewerId: UserId, expectedSubscriberId: UserId): Boolean
    }
}
