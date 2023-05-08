package app.meetacy.backend.database.integration.invitation.accept

import app.meetacy.backend.usecase.invitations.accept.AcceptInvitationUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseAcceptInvitationStorage(db: Database): AcceptInvitationUsecase.Storage {
    // TODO
}