package app.meetacy.backend.database.integration.users.add

import app.meetacy.backend.database.users.UsersTable
import app.meetacy.backend.types.AccessIdentity
import app.meetacy.backend.types.FileIdentity
import app.meetacy.backend.usecase.users.add.AddAvatarUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseAddAvatarStorage(db: Database) : AddAvatarUsecase.Storage {
    private val usersTable = UsersTable(db)
    override suspend fun addAvatar(accessIdentity: AccessIdentity, avatarIdentity: FileIdentity) {
        usersTable.addAvatar(accessIdentity, avatarIdentity)
    }
}
