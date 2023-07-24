@file:Suppress("UNUSED_VARIABLE")

package app.meetacy.backend.infrastructure.integrations.invitations.create

import app.meetacy.backend.database.integration.invitations.create.DatabaseCreateInvitationStorage
import app.meetacy.backend.endpoint.invitations.create.CreateInvitationRepository
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.backend.infrastructure.integrations.auth.authRepository
import app.meetacy.backend.infrastructure.integrations.invitations.getInvitationsViewsRepository
import app.meetacy.backend.infrastructure.integrations.notifications.add.addNotificationUsecase
import app.meetacy.backend.usecase.integration.invitations.create.UsecaseCreateInvitationRepository
import app.meetacy.backend.usecase.invitations.create.CreateInvitationUsecase
import app.meetacy.di.DI
import app.meetacy.di.accessHashGenerator
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.createInvitationRepository: CreateInvitationRepository by Dependency

fun DIBuilder.createInvitationRepository() {
    getInvitationsViewsRepository()
    val createInvitationRepository by singleton<CreateInvitationRepository> {
        UsecaseCreateInvitationRepository(
            usecase = CreateInvitationUsecase(
                authRepository,
                DatabaseCreateInvitationStorage(database, addNotificationUsecase),
                accessHashGenerator,
                getInvitationsViewsRepository
            )
        )
    }
}
