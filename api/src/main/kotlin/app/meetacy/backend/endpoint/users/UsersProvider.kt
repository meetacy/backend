package app.meetacy.backend.endpoint.users

import app.meetacy.backend.domain.User

interface UsersProvider {
    suspend fun getUsers(): List<User>
}
