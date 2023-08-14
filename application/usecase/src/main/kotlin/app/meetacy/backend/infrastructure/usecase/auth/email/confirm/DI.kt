package app.meetacy.backend.infrastructure.usecase.auth.email.confirm

import app.meetacy.backend.feature.auth.usecase.email.ConfirmEmailUsecase
import app.meetacy.backend.feature.auth.usecase.integration.email.confirm.UsecaseConfirmEmailRepository
import app.meetacy.backend.feature.email.endpoints.confirm.ConfirmEmailRepository
import app.meetacy.backend.infrastructure.database.email.confirm.confirmEmailStorage
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.confirmEmailRepository: ConfirmEmailRepository by Dependency

fun DIBuilder.confirmEmailRepository() {
    val confirmEmailRepository by singleton<ConfirmEmailRepository> {
        UsecaseConfirmEmailRepository(
            usecase = ConfirmEmailUsecase(
                confirmEmailStorage
            )
        )
    }
}
