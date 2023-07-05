package app.meetacy.backend.infrastructure.integrations.invitations.accept

import app.meetacy.backend.database.integration.invitations.accept.DatabaseAcceptInvitationStorage
import app.meetacy.backend.endpoint.invitations.accept.AcceptInvitationRepository
import app.meetacy.backend.infrastructure.integrations.auth.authRepository
import app.meetacy.backend.usecase.integration.invitations.accept.UsecaseAcceptInvitationRepository
import app.meetacy.backend.usecase.invitations.accept.AcceptInvitationUsecase
import app.meetacy.backend.usecase.types.AuthRepository
import org.jetbrains.exposed.sql.Database

fun acceptInvitationRepository(
    db: Database,
    authRepository: AuthRepository = authRepository(db)
): AcceptInvitationRepository = UsecaseAcceptInvitationRepository(
    usecase = AcceptInvitationUsecase(
        authRepository = authRepository,
        storage = DatabaseAcceptInvitationStorage(db)
    )
)
