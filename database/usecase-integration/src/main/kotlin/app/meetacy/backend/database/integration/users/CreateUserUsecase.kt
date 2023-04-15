package app.meetacy.backend.database.integration.users

import app.meetacy.backend.database.users.UsersTable
import app.meetacy.backend.types.access.AccessHash
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.auth.CreateUserUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseCreateUserStorage(db: Database) : CreateUserUsecase.Storage {
    private val usersTable = UsersTable(db)
    override suspend fun addUser(accessHash: AccessHash, nickname: String): UserId =
        usersTable.addUser(accessHash, nickname).identity.userId
}
