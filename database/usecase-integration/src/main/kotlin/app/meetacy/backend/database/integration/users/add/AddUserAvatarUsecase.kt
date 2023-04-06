package app.meetacy.backend.database.integration.users.add

import app.meetacy.backend.database.users.UsersTable
import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.file.FileIdentity
import app.meetacy.backend.usecase.users.avatar.add.AddUserAvatarUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseAddUserAvatarStorage(db: Database) : AddUserAvatarUsecase.Storage {
    private val usersTable = UsersTable(db)
    override suspend fun addAvatar(accessIdentity: AccessIdentity, avatarIdentity: FileIdentity) {
        usersTable.addAvatar(accessIdentity, avatarIdentity)
    }
}
