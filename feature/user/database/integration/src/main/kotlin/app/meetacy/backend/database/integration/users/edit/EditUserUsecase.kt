package app.meetacy.backend.database.integration.users.edit

import app.meetacy.backend.database.integration.types.mapToUsecase
import app.meetacy.backend.database.users.UsersStorage
import app.meetacy.backend.types.file.FileId
import app.meetacy.backend.types.optional.Optional
import app.meetacy.backend.types.users.UserId
import app.meetacy.backend.types.users.Username
import app.meetacy.backend.usecase.types.FullUser
import app.meetacy.backend.usecase.users.edit.EditUserUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseEditUserStorage(db: Database) : EditUserUsecase.Storage {
    private val usersStorage = UsersStorage(db)

    override suspend fun editUser(
        userId: UserId, nickname: Optional<String>,
        username: Optional<Username?>,
        avatarId: Optional<FileId?>
    ): FullUser =
        usersStorage.editUser(userId, nickname, username, avatarId).mapToUsecase()

    override suspend fun isOccupied(username: Username): Boolean =
        usersStorage.isUsernameOccupied(username)
}
