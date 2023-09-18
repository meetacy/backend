package app.meetacy.backend.feature.users.usecase.integration.edit

import app.meetacy.backend.feature.users.database.users.UsersStorage
import app.meetacy.backend.feature.users.usecase.edit.EditUserUsecase
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.types.files.FileId
import app.meetacy.backend.types.files.FilesRepository
import app.meetacy.backend.types.optional.Optional
import app.meetacy.backend.types.users.FullUser
import app.meetacy.backend.types.users.UserId
import app.meetacy.backend.types.users.Username
import app.meetacy.backend.types.utf8Checker.Utf8Checker
import app.meetacy.di.builder.DIBuilder

internal fun DIBuilder.editUserUsecase() {
    val editUserUsecase by singleton {
        val authRepository: AuthRepository by getting
        val filesRepository: FilesRepository by getting
        val utf8Checker: Utf8Checker by getting
        val usersStorage: UsersStorage by getting
        val storage = object : EditUserUsecase.Storage {

            override suspend fun editUser(
                userId: UserId, nickname: Optional<String>,
                username: Optional<Username?>,
                avatarId: Optional<FileId?>
            ): FullUser =
                usersStorage.editUser(userId, nickname, username, avatarId)

            override suspend fun isOccupied(username: Username): Boolean =
                usersStorage.isUsernameOccupied(username)
        }
        EditUserUsecase(storage, authRepository, filesRepository, utf8Checker)
    }
}
