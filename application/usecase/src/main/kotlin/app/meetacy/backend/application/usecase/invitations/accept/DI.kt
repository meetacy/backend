package app.meetacy.backend.application.usecase.invitations.accept

import app.meetacy.backend.feature.invitations.endpoints.accept.AcceptInvitationRepository
import app.meetacy.backend.application.database.invitations.accept.acceptInvitationStorage
import app.meetacy.backend.feature.invitations.usecase.integration.invitations.accept.UsecaseAcceptInvitationRepository
import app.meetacy.backend.feature.invitations.usecase.AcceptInvitationUsecase
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.acceptInvitationRepository: AcceptInvitationRepository by Dependency

fun DIBuilder.acceptInvitationRepository() {
    val acceptInvitationRepository by singleton<AcceptInvitationRepository> {
        UsecaseAcceptInvitationRepository(
            usecase = AcceptInvitationUsecase(
                authRepository = get(),
                acceptInvitationStorage
            )
        )
    }
}
