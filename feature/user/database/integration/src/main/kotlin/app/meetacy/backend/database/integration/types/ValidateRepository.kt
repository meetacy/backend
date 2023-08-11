package app.meetacy.backend.database.integration.types

import app.meetacy.backend.database.users.UsersStorage
import app.meetacy.backend.types.users.Username
import app.meetacy.backend.types.users.ValidateRepository
import org.jetbrains.exposed.sql.Database

class DatabaseValidateRepository(db: Database) : ValidateRepository {
    private val usersStorage = UsersStorage(db)
    override suspend fun isOccupied(username: Username): Boolean =
        usersStorage.isUsernameOccupied(username)
}
