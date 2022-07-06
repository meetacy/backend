package app.meetacy.backend.integration.mockEndpoint

import app.meetacy.backend.domain.User
import app.meetacy.backend.endpoint.users.UsersProvider
import app.meetacy.backend.mock.MockUsers

object MockUsersProvider : UsersProvider {
    override suspend fun getUsers(): List<User> =
        MockUsers.getUsers()
}
