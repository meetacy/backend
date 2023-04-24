package app.meetacy.backend.database.integration.users.edit

import app.meetacy.backend.database.integration.types.mapToUsecase
import app.meetacy.backend.database.users.UsersTable
import app.meetacy.backend.types.Optional
import app.meetacy.backend.types.file.FileId
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.types.user.Username
import app.meetacy.backend.usecase.types.FullUser
import app.meetacy.backend.usecase.users.edit.EditUserUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseEditUserStorage(db: Database) : EditUserUsecase.Storage {
    private val usersTable = UsersTable(db)

    override suspend fun editUser(
        userId: UserId, nickname: String?,
        username: Optional<Username?>,
        avatarId: Optional<FileId?>
    ): FullUser? =
        usersTable.editUser(userId, nickname, username, avatarId)?.mapToUsecase()

}
