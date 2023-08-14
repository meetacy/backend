package app.meetacy.backend.feature.auth.usecase.integration

import app.meetacy.backend.feature.users.database.users.UsersStorage
import app.meetacy.backend.feature.auth.usecase.CreateUserUsecase
import app.meetacy.backend.types.access.AccessHash
import app.meetacy.backend.types.users.UserId
import app.meetacy.di.builder.DIBuilder

internal fun DIBuilder.createTokenUsecase() {
    val createTokenUsecase by singleton {
        CreateUserUsecase(
            generator = get(),
            storage = DatabaseCreateUserStorage(
                usersStorage = get()
            )
        )
    }
}

private class DatabaseCreateUserStorage(
    private val usersStorage: UsersStorage
) : CreateUserUsecase.Storage {
    override suspend fun addUser(accessHash: AccessHash, nickname: String): UserId =
        usersStorage.addUser(accessHash, nickname).identity.id
}

