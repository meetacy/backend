package app.meetacy.backend.feature.email.usecase.integration

import app.meetacy.backend.feature.email.database.ConfirmationStorage
import app.meetacy.backend.feature.email.database.DatabaseEmailSender
import app.meetacy.backend.feature.email.database.DatabaseEmailText
import app.meetacy.backend.feature.users.database.users.UsersStorage
import app.meetacy.backend.types.users.UserId
import app.meetacy.backend.feature.email.usecase.LinkEmailUsecase
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.types.generator.AccessHashGenerator
import app.meetacy.di.builder.DIBuilder

internal fun DIBuilder.linkEmailUsecase() {
    val linkEmailUsecase by singleton {
        val confirmationStorage: ConfirmationStorage by getting
        val usersStorage: UsersStorage by getting
        val accessHashGenerator: AccessHashGenerator by getting
        val authRepository: AuthRepository by getting

        val storage = object : LinkEmailUsecase.Storage {
            override suspend fun isEmailOccupied(
                email: String
            ): Boolean = usersStorage.isEmailOccupied(email)

            override suspend fun updateEmail(
                userId: UserId,
                email: String
            ) { usersStorage.updateEmail(userId, email) }

            override suspend fun addConfirmationHash(
                userId: UserId,
                email: String,
                confirmationHash: String
            ) { confirmationStorage.addHash(userId, email, confirmationHash) }
        }

        val mailer = object : LinkEmailUsecase.Mailer {
            override fun sendEmailOccupiedMessage(email: String) = DatabaseEmailSender.sendEmail(
                email = email,
                text = DatabaseEmailText.getOccupiedText()
            )

            override fun sendConfirmationMessage(
                email: String,
                confirmationHash: String
            ) = DatabaseEmailSender.sendEmail(
                email = email,
                text = DatabaseEmailText.getConfirmationText(email, confirmationHash)
            )
        }

        LinkEmailUsecase(storage, mailer, accessHashGenerator, authRepository)
    }
}
