package app.meetacy.backend.infrastructure.database.email.confirm

import app.meetacy.backend.database.integration.email.DatabaseConfirmEmailStorage
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.backend.usecase.email.ConfirmEmailUsecase
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.confirmEmailUsecase: ConfirmEmailUsecase by Dependency

fun DIBuilder.confirmEmail() {
    val confirmEmailUsecase by singleton<ConfirmEmailUsecase> {
        ConfirmEmailUsecase(
            storage = DatabaseConfirmEmailStorage(database)
        )
    }
}
