package app.meetacy.backend.application.database.invitations.create

import app.meetacy.backend.feature.invitations.database.integration.invitations.create.DatabaseCreateInvitationStorage
import app.meetacy.backend.application.database.database
import app.meetacy.backend.application.database.notifications.add.addNotificationUsecase
import app.meetacy.backend.feature.invitations.usecase.create.CreateInvitationUsecase
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.createInvitationStorage: CreateInvitationUsecase.Storage by Dependency

fun DIBuilder.createInvitation() {
    val createInvitationStorage by singleton<CreateInvitationUsecase.Storage> {
        DatabaseCreateInvitationStorage(
            database,
            addNotificationUsecase
        )
    }
}
