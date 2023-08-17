package app.meetacy.backend.infrastructure.usecase.invitations.create

import app.meetacy.backend.feature.invitations.endpoints.create.CreateInvitationRepository
import app.meetacy.backend.infrastructure.database.invitations.create.createInvitationStorage
import app.meetacy.backend.infrastructure.database.invitations.view.getInvitationsViewsRepository
import app.meetacy.backend.feature.invitations.usecase.integration.invitations.create.UsecaseCreateInvitationRepository
import app.meetacy.backend.feature.invitations.usecase.create.CreateInvitationUsecase
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.createInvitationRepository: CreateInvitationRepository by Dependency

fun DIBuilder.createInvitationRepository() {
    getInvitationsViewsRepository()
    val createInvitationRepository by singleton<CreateInvitationRepository> {
        UsecaseCreateInvitationRepository(
            usecase = CreateInvitationUsecase(
                authRepository = get(),
                storage = createInvitationStorage,
                hashGenerator = get(),
                invitationsRepository = getInvitationsViewsRepository
            )
        )
    }
}
