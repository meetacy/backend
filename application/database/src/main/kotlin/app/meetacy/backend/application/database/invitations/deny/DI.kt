package app.meetacy.backend.application.database.invitations.deny

import app.meetacy.backend.feature.invitations.database.integration.invitations.deny.DatabaseDenyInvitationStorage
import app.meetacy.backend.application.database.database
import app.meetacy.backend.feature.invitations.usecase.deny.DenyInvitationUsecase
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.denyInvitationStorage: DenyInvitationUsecase.Storage by Dependency

fun DIBuilder.denyInvitation() {
    val denyInvitationStorage by singleton<DenyInvitationUsecase.Storage> {
        DatabaseDenyInvitationStorage(database)
    }
}
