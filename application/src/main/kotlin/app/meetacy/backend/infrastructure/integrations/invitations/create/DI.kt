package app.meetacy.backend.infrastructure.integrations.invitations.create

import app.meetacy.backend.database.integration.invitations.create.DatabaseCreateInvitationStorage
import app.meetacy.backend.di.DI
import app.meetacy.backend.di.builder.DIBuilder
import app.meetacy.backend.di.dependency.Dependency
import app.meetacy.backend.endpoint.invitations.create.CreateInvitationRepository
import app.meetacy.backend.hash.integration.DefaultHashGenerator
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.backend.infrastructure.integrations.auth.authRepository
import app.meetacy.backend.infrastructure.integrations.invitations.getInvitationsViewsRepository
import app.meetacy.backend.infrastructure.integrations.notifications.add.addNotificationUsecase
import app.meetacy.backend.usecase.integration.invitations.create.UsecaseCreateInvitationRepository
import app.meetacy.backend.usecase.invitations.create.CreateInvitationUsecase

val DI.createInvitationRepository: CreateInvitationRepository by Dependency

fun DIBuilder.createInvitationRepository() {
    getInvitationsViewsRepository()
    val createInvitationRepository by singleton<CreateInvitationRepository> {
        UsecaseCreateInvitationRepository(
            usecase = CreateInvitationUsecase(
                authRepository,
                DatabaseCreateInvitationStorage(database, addNotificationUsecase),
                DefaultHashGenerator,
                getInvitationsViewsRepository
            )
        )
    }
}
