package app.meetacy.backend.integration.mock.getUser

import app.meetacy.backend.endpoint.users.GetUserParams
import app.meetacy.backend.endpoint.users.UserProvider
import app.meetacy.backend.endpoint.users.UserResponse
import app.meetacy.backend.mock.storage.MockUser
import app.meetacy.backend.mock.storage.TokensStorage
import app.meetacy.backend.mock.storage.UsersStorage

object MockUserProviderIntegration : UserProvider {
    override fun getUser(getUserParams: GetUserParams): UserResponse? {
        // id = null
        // accessHash = null
        // accessToken = "..."

        val userId = TokensStorage.getToken(getUserParams.accessToken)?.ownerId

        val id = if (getUserParams.id == null && getUserParams.accessHash == null) {
            userId
        } else {
            getUserParams.id
        } ?: return null

        val user: MockUser = UsersStorage.getUser(id) ?: return null

        if (getUserParams.accessHash != user.accessHash &&
                getUserParams.accessHash != null)
            return null

        return if (userId == id) {
            UserResponse(
                id = user.id,
                nickname = user.nickname,
                email = user.email,
                emailVerified = user.emailVerified.takeIf { user.email != null }
            )
        } else {
            UserResponse(
                id = user.id,
                nickname = user.nickname,
                email = null,
                emailVerified = null
            )
        }
    }
}
