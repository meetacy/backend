package app.meetacy.backend.feature.users.usecase.integration.username.available

import app.meetacy.backend.feature.users.database.users.UsersStorage
import app.meetacy.backend.feature.users.usecase.username.available.UsernameAvailableUsecase
import app.meetacy.backend.types.users.Username
import app.meetacy.di.builder.DIBuilder

internal fun DIBuilder.usernameAvailableUsecase() {
    val usernameAvailableUsecase by singleton {
        val usersStorage: UsersStorage by getting
        val storage = object : UsernameAvailableUsecase.Storage {
            override suspend fun isOccupied(username: Username): Boolean =
                usersStorage.isUsernameOccupied(username)
        }
        UsernameAvailableUsecase(storage)
    }
}
