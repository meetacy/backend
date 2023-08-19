package app.meetacy.backend.application.database.email.confirm

import app.meetacy.backend.feature.email.database.integration.DatabaseConfirmEmailStorage
import app.meetacy.backend.application.database.database
import app.meetacy.backend.feature.email.usecase.ConfirmEmailUsecase
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.confirmEmailStorage: ConfirmEmailUsecase.Storage by Dependency

fun DIBuilder.confirmEmail() {
    val confirmEmailStorage by singleton<ConfirmEmailUsecase.Storage> {
        DatabaseConfirmEmailStorage(database)
    }
}
