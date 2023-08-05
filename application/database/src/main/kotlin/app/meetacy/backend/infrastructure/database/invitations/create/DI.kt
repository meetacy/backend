package app.meetacy.backend.infrastructure.database.invitations.create

import app.meetacy.backend.database.integration.invitations.create.DatabaseCreateInvitationStorage
import app.meetacy.backend.database.integration.notifications.AddNotificationUsecase
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.backend.infrastructure.database.updates.updatesMiddleware
import app.meetacy.backend.usecase.invitations.create.CreateInvitationUsecase
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.createInvitationStorage: CreateInvitationUsecase.Storage by Dependency

fun DIBuilder.createInvitation() {
    val createInvitationStorage by singleton<CreateInvitationUsecase.Storage> {
        DatabaseCreateInvitationStorage(
            database,
            addNotificationUsecase = AddNotificationUsecase(
                database,
                updatesMiddleware
            )
        )
    }
}
