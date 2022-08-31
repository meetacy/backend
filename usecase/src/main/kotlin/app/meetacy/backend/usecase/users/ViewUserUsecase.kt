package app.meetacy.backend.usecase.users

import app.meetacy.backend.types.UserId
import app.meetacy.backend.types.UserIdentity
import app.meetacy.backend.usecase.types.FullUser
import app.meetacy.backend.usecase.types.UserView

class ViewUserUsecase {
    fun viewUsers(viewerId: UserId, users: List<FullUser>): List<UserView> = users.map { user ->
        with(user) {
            UserView(
                identity, nickname,
                email = if (viewerId == user.identity.userId) email else null,
                emailVerified = if (viewerId == user.identity.userId) emailVerified else null
            )
        }
    }

    fun viewUser(viewerId: UserId, user: FullUser) = viewUsers(viewerId, listOf(user)).first()
}
