package app.meetacy.backend.infrastructure.integrations.invitations.deny

import app.meetacy.backend.endpoint.invitations.deny.DenyInvitationRepository
import app.meetacy.backend.infrastructure.database.auth.authRepository
import app.meetacy.backend.infrastructure.database.invitations.deny.denyInvitationStorage
import app.meetacy.backend.usecase.integration.invitations.deny.UsecaseDenyInvitationRepository
import app.meetacy.backend.usecase.invitations.deny.DenyInvitationUsecase
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.denyInvitationRepository: DenyInvitationRepository by Dependency

fun DIBuilder.denyInvitationRepository() {
    val denyInvitationRepository by singleton<DenyInvitationRepository> {
        UsecaseDenyInvitationRepository(
            usecase = DenyInvitationUsecase(
                authRepository = authRepository,
                storage = denyInvitationStorage
            )
        )
    }
}
