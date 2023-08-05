@file:Suppress("UNUSED_VARIABLE")

package app.meetacy.backend.infrastructure.integrations.invitations.accept

import app.meetacy.backend.endpoint.invitations.accept.AcceptInvitationRepository
import app.meetacy.backend.infrastructure.database.auth.authRepository
import app.meetacy.backend.infrastructure.database.invitations.accept.acceptInvitationStorage
import app.meetacy.backend.usecase.integration.invitations.accept.UsecaseAcceptInvitationRepository
import app.meetacy.backend.usecase.invitations.accept.AcceptInvitationUsecase
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.acceptInvitationRepository: AcceptInvitationRepository by Dependency

fun DIBuilder.acceptInvitationRepository() {
    val acceptInvitationRepository by singleton<AcceptInvitationRepository> {
        UsecaseAcceptInvitationRepository(
            usecase = AcceptInvitationUsecase(
                authRepository,
                acceptInvitationStorage
            )
        )
    }
}
