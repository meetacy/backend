package app.meetacy.backend.infrastructure.factories.invitations.deny

import app.meetacy.backend.database.integration.invitations.deny.DatabaseDenyInvitationStorage
import app.meetacy.backend.endpoint.invitations.deny.DenyInvitationRepository
import app.meetacy.backend.infrastructure.factories.auth.authRepository
import app.meetacy.backend.usecase.integration.invitations.deny.UsecaseDenyInvitationRepository
import app.meetacy.backend.usecase.invitations.deny.DenyInvitationUsecase
import app.meetacy.backend.usecase.types.AuthRepository
import org.jetbrains.exposed.sql.Database

fun denyInvitationRepository(
    db: Database,
    authRepository: AuthRepository = authRepository(db)
): DenyInvitationRepository = UsecaseDenyInvitationRepository(
    usecase = DenyInvitationUsecase(
        authRepository = authRepository,
        storage = DatabaseDenyInvitationStorage(db)
    )
)
