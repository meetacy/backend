package app.meetacy.backend.feature.auth.database.integration.types

import app.meetacy.backend.database.users.UsersStorage
import app.meetacy.backend.feature.auth.usecase.types.ValidateRepository
import app.meetacy.backend.types.user.Username
import org.jetbrains.exposed.sql.Database

class DatabaseValidateRepository(db: Database) : ValidateRepository {
    private val usersStorage = UsersStorage(db)
    override suspend fun isOccupied(username: Username): Boolean =
        usersStorage.isUsernameOccupied(username)
}
