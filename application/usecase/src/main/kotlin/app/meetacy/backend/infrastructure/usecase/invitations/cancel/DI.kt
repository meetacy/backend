package app.meetacy.backend.infrastructure.usecase.invitations.cancel

import app.meetacy.backend.feature.invitations.endpoints.cancel.CancelInvitationRepository
import app.meetacy.backend.infrastructure.database.auth.authRepository
import app.meetacy.backend.infrastructure.database.invitations.cancel.cancelInvitationStorage
import app.meetacy.backend.feature.invitations.usecase.integration.invitations.cancel.UsecaseCancelInvitationRepository
import app.meetacy.backend.feature.invitations.usecase.invitations.cancel.CancelInvitationUsecase
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.cancelInvitationRepository: CancelInvitationRepository by Dependency

fun DIBuilder.cancelInvitationRepository() {
    val cancelInvitationRepository by singleton<CancelInvitationRepository> {
        UsecaseCancelInvitationRepository(
            usecase = CancelInvitationUsecase(
                authRepository,
                cancelInvitationStorage
            )
        )
    }
}
