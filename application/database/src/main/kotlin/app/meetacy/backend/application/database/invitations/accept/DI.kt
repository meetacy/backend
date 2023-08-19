package app.meetacy.backend.application.database.invitations.accept

import app.meetacy.backend.feature.invitations.database.integration.invitations.accept.DatabaseAcceptInvitationStorage
import app.meetacy.backend.application.database.database
import app.meetacy.backend.feature.invitations.usecase.AcceptInvitationUsecase
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.acceptInvitationStorage: AcceptInvitationUsecase.Storage by Dependency

fun DIBuilder.acceptInvitation() {
    val acceptInvitationStorage by singleton<AcceptInvitationUsecase.Storage> {
        DatabaseAcceptInvitationStorage(database)
    }
}
