package app.meetacy.backend.database.integration.users

import app.meetacy.backend.database.users.UsersTable
import app.meetacy.backend.types.AccessHash
import app.meetacy.backend.types.UserId
import app.meetacy.backend.usecase.users.CreateUserUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseCreateUserStorage(private val db: Database) : CreateUserUsecase.Storage {
    override suspend fun addUser(accessHash: AccessHash, nickname: String): UserId =
        UsersTable(db).addUser(accessHash, nickname).id
}