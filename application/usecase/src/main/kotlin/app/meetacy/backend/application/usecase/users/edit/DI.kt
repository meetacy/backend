package app.meetacy.backend.application.usecase.users.edit

import app.meetacy.backend.feature.users.endpoints.edit.EditUserRepository
import app.meetacy.backend.infrastructure.database.files.filesRepository
import app.meetacy.backend.infrastructure.database.users.edit.editUserStorage
import app.meetacy.backend.feature.users.usecase.integration.users.edit.UsecaseEditUserRepository
import app.meetacy.backend.feature.users.usecase.edit.EditUserUsecase
import app.meetacy.backend.types.integration.utf8Checker.DefaultUtf8Checker
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.editUserRepository: EditUserRepository by Dependency

fun DIBuilder.editUserRepository() {
    val editUserRepository by singleton<EditUserRepository> {
        UsecaseEditUserRepository(
            usecase = EditUserUsecase(
                storage = editUserStorage,
                authRepository = get(),
                filesRepository = filesRepository,
                utf8Checker = DefaultUtf8Checker
            )
        )
    }
}
