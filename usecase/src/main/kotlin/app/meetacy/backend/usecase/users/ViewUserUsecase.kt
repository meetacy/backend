package app.meetacy.backend.usecase.users

import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.types.FullUser
import app.meetacy.backend.usecase.types.UserView

class ViewUserUsecase {
    fun viewUsers(viewerId: UserId, users: List<FullUser>): List<UserView> = users.map { user ->
        with(user) {
            UserView(
                isSelf = viewerId == user.identity.userId,
                identity = identity,
                nickname = nickname,
                email = if (viewerId == user.identity.userId) email else null,
                emailVerified = if (viewerId == user.identity.userId) emailVerified else null,
                avatarIdentity = avatarIdentity
            )
        }
    }

    fun viewUser(viewerId: UserId, user: FullUser) = viewUsers(viewerId, listOf(user)).first()
}
