package app.meetacy.backend.infrastructure.database.users.edit

import app.meetacy.backend.database.integration.users.edit.DatabaseEditUserStorage
import app.meetacy.backend.feature.auth.usecase.users.edit.EditUserUsecase
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.editUserStorage: EditUserUsecase.Storage by Dependency

fun DIBuilder.editUser() {
    val editUserStorage by singleton<EditUserUsecase.Storage> {
        DatabaseEditUserStorage(database)
    }
}
