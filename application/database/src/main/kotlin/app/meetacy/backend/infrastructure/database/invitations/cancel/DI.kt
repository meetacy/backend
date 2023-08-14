package app.meetacy.backend.infrastructure.database.invitations.cancel

import app.meetacy.backend.database.integration.invitations.cancel.DatabaseCancelInvitationStorage
import app.meetacy.backend.feature.auth.usecase.invitations.cancel.CancelInvitationUsecase
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.cancelInvitationStorage: CancelInvitationUsecase.Storage by Dependency

fun DIBuilder.cancelInvitation() {
    val cancelInvitationStorage by singleton<CancelInvitationUsecase.Storage> {
        DatabaseCancelInvitationStorage(database)
    }
}
