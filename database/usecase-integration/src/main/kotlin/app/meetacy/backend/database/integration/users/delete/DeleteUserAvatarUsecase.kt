package app.meetacy.backend.database.integration.users.delete

import app.meetacy.backend.database.users.UsersTable
import app.meetacy.backend.types.AccessIdentity
import app.meetacy.backend.usecase.users.avatar.delete.DeleteUserAvatarUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseDeleteUserAvatarStorage(db: Database) : DeleteUserAvatarUsecase.Storage {
    private val usersTable = UsersTable(db)

    override suspend fun deleteAvatar(accessIdentity: AccessIdentity) {
        usersTable.deleteAvatar(accessIdentity.userId)
    }
}