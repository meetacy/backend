@file:Suppress("UNUSED_VARIABLE")

package app.meetacy.backend.infrastructure.integrations.users.edit

import app.meetacy.backend.database.integration.users.edit.DatabaseEditUserStorage
import app.meetacy.backend.endpoint.users.edit.EditUserRepository
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.backend.infrastructure.database.auth.authRepository
import app.meetacy.backend.infrastructure.database.files.filesRepository
import app.meetacy.backend.usecase.integration.users.edit.UsecaseEditUserRepository
import app.meetacy.backend.usecase.users.edit.EditUserUsecase
import app.meetacy.backend.utf8.integration.DefaultUtf8Checker
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.editUserRepository: EditUserRepository by Dependency

fun DIBuilder.editUserRepository() {
    val editUserRepository by singleton<EditUserRepository> {
        UsecaseEditUserRepository(
            usecase = EditUserUsecase(
                storage = DatabaseEditUserStorage(database),
                authRepository = authRepository,
                filesRepository = filesRepository,
                utf8Checker = DefaultUtf8Checker
            )
        )
    }
}
