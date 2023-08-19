package app.meetacy.backend.application.database.users.edit

import app.meetacy.backend.feature.users.database.integration.users.edit.DatabaseEditUserStorage
import app.meetacy.backend.application.database.database
import app.meetacy.backend.feature.users.usecase.edit.EditUserUsecase
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.editUserStorage: EditUserUsecase.Storage by Dependency

fun DIBuilder.editUser() {
    val editUserStorage by singleton<EditUserUsecase.Storage> {
        DatabaseEditUserStorage(database)
    }
}
