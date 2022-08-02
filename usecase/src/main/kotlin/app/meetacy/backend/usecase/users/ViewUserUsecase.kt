package app.meetacy.backend.usecase.users

import app.meetacy.backend.types.UserId
import app.meetacy.backend.usecase.types.FullUser
import app.meetacy.backend.usecase.types.UserView

class ViewUserUsecase {
    fun viewUsers(viewerId: UserId, users: List<FullUser>): List<UserView> = users.map { user ->
        with(user) {
            UserView(
                id, accessHash, nickname,
                email = if (viewerId == user.id) email else null,
                emailVerified = if (viewerId == user.id) emailVerified else null
            )
        }
    }

    fun viewUser(viewerId: UserId, user: FullUser) = viewUsers(viewerId, listOf(user)).first()
}
