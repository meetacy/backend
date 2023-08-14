package app.meetacy.backend.infrastructure.database.email.confirm

import app.meetacy.backend.database.integration.email.DatabaseConfirmEmailStorage
import app.meetacy.backend.feature.auth.usecase.email.ConfirmEmailUsecase
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.confirmEmailStorage: ConfirmEmailUsecase.Storage by Dependency

fun DIBuilder.confirmEmail() {
    val confirmEmailStorage by singleton<ConfirmEmailUsecase.Storage> {
        DatabaseConfirmEmailStorage(database)
    }
}
