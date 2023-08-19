package app.meetacy.backend.application.endpoints.invitations.deny

import app.meetacy.backend.feature.invitations.endpoints.deny.DenyInvitationRepository
import app.meetacy.backend.application.database.invitations.deny.denyInvitationStorage
import app.meetacy.backend.feature.invitations.usecase.integration.invitations.deny.UsecaseDenyInvitationRepository
import app.meetacy.backend.feature.invitations.usecase.deny.DenyInvitationUsecase
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.denyInvitationRepository: DenyInvitationRepository by Dependency

fun DIBuilder.denyInvitationRepository() {
    val denyInvitationRepository by singleton<DenyInvitationRepository> {
        UsecaseDenyInvitationRepository(
            usecase = DenyInvitationUsecase(
                authRepository = get(),
                storage = denyInvitationStorage
            )
        )
    }
}
