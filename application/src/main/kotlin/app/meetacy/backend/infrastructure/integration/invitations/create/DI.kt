package app.meetacy.backend.infrastructure.integration.invitations.create

import app.meetacy.backend.endpoint.invitations.create.CreateInvitationRepository
import app.meetacy.backend.infrastructure.database.auth.authRepository
import app.meetacy.backend.infrastructure.database.invitations.create.createInvitationStorage
import app.meetacy.backend.infrastructure.integration.invitations.getInvitationsViewsRepository
import app.meetacy.backend.usecase.integration.invitations.create.UsecaseCreateInvitationRepository
import app.meetacy.backend.usecase.invitations.create.CreateInvitationUsecase
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.createInvitationRepository: CreateInvitationRepository by Dependency

fun DIBuilder.createInvitationRepository() {
    getInvitationsViewsRepository()
    val createInvitationRepository by singleton<CreateInvitationRepository> {
        UsecaseCreateInvitationRepository(
            usecase = CreateInvitationUsecase(
                authRepository = authRepository,
                storage = createInvitationStorage,
                hashGenerator = get(),
                invitationsRepository = getInvitationsViewsRepository
            )
        )
    }
}
