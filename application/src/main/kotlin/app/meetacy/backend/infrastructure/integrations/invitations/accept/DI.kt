package app.meetacy.backend.infrastructure.integrations.invitations.accept

import app.meetacy.backend.database.integration.invitations.accept.DatabaseAcceptInvitationStorage
import app.meetacy.backend.di.DI
import app.meetacy.backend.di.builder.DIBuilder
import app.meetacy.backend.di.dependency.Dependency
import app.meetacy.backend.endpoint.invitations.accept.AcceptInvitationRepository
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.backend.infrastructure.integrations.auth.authRepository
import app.meetacy.backend.usecase.integration.invitations.accept.UsecaseAcceptInvitationRepository
import app.meetacy.backend.usecase.invitations.accept.AcceptInvitationUsecase

val DI.acceptInvitationRepository: AcceptInvitationRepository by Dependency

fun DIBuilder.acceptInvitationRepository() {
    val acceptInvitationRepository by singleton<AcceptInvitationRepository> {
        UsecaseAcceptInvitationRepository(
            usecase = AcceptInvitationUsecase(
                authRepository,
                DatabaseAcceptInvitationStorage(database)
            )
        )
    }
}
