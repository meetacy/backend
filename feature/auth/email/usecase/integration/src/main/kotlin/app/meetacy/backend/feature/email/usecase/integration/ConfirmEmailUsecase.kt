package app.meetacy.backend.feature.email.usecase.integration

import app.meetacy.backend.feature.email.database.ConfirmationStorage
import app.meetacy.backend.feature.users.database.users.UsersStorage
import app.meetacy.backend.types.users.UserId
import app.meetacy.backend.feature.email.usecase.ConfirmEmailUsecase
import app.meetacy.di.builder.DIBuilder

internal fun DIBuilder.confirmEmailUsecase() {
    val confirmEmailUsecase by singleton {
        val confirmationStorage: ConfirmationStorage by getting
        val usersStorage: UsersStorage by getting

        val storage = object : ConfirmEmailUsecase.Storage {
            override suspend fun getConfirmHashOwnerId(email: String, confirmHash: String): UserId? =
                confirmationStorage.getConfirmHashOwnerId(email, confirmHash)

            override suspend fun deleteHashes(email: String) =
                confirmationStorage.deleteHashes(email)

            override suspend fun verifyEmail(userIdentity: UserId) {
                usersStorage.verifyEmail(userIdentity)
            }
        }

        ConfirmEmailUsecase(storage)
    }
}
