@file:Suppress("UNUSED_VARIABLE")

package app.meetacy.backend.infrastructure.integrations.auth.email.confirm

import app.meetacy.backend.endpoint.auth.email.confirm.ConfirmEmailRepository
import app.meetacy.backend.infrastructure.database.email.confirm.confirmEmailStorage
import app.meetacy.backend.usecase.email.ConfirmEmailUsecase
import app.meetacy.backend.usecase.integration.email.confirm.UsecaseConfirmEmailRepository
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
