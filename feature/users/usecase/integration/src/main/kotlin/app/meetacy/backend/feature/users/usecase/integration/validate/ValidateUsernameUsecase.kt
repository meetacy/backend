package app.meetacy.backend.feature.users.usecase.integration.validate

import app.meetacy.backend.feature.users.database.users.UsersStorage
import app.meetacy.backend.feature.users.usecase.validate.ValidateUsernameUsecase
import app.meetacy.backend.types.users.Username
import app.meetacy.di.builder.DIBuilder

internal fun DIBuilder.validateUsernameUsecase() {
    val validateUsernameUsecase by singleton {
        val usersStorage: UsersStorage by getting
        val storage = object : ValidateUsernameUsecase.Storage {
            override suspend fun isOccupied(username: Username): Boolean =
                usersStorage.isUsernameOccupied(username)
        }
        ValidateUsernameUsecase(storage)
    }
}
