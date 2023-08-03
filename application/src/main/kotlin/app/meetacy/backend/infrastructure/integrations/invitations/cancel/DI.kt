@file:Suppress("UNUSED_VARIABLE")

package app.meetacy.backend.infrastructure.integrations.invitations.cancel

import app.meetacy.backend.database.integration.invitations.cancel.DatabaseCancelInvitationStorage
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency
import app.meetacy.backend.endpoint.invitations.cancel.CancelInvitationRepository
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.backend.infrastructure.database.auth.authRepository
import app.meetacy.backend.usecase.integration.invitations.cancel.UsecaseCancelInvitationRepository
import app.meetacy.backend.usecase.invitations.cancel.CancelInvitationUsecase

val DI.cancelInvitationRepository: CancelInvitationRepository by Dependency

fun DIBuilder.cancelInvitationRepository() {
    val cancelInvitationRepository by singleton<CancelInvitationRepository> {
        UsecaseCancelInvitationRepository(
            usecase = CancelInvitationUsecase(
                authRepository,
                DatabaseCancelInvitationStorage(database)
            )
        )
    }
}
