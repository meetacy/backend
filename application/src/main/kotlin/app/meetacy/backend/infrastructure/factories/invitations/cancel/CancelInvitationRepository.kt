package app.meetacy.backend.infrastructure.factories.invitations.cancel

import app.meetacy.backend.database.integration.invitations.cancel.DatabaseCancelInvitationStorage
import app.meetacy.backend.endpoint.invitations.cancel.CancelInvitationRepository
import app.meetacy.backend.infrastructure.factories.auth.authRepository
import app.meetacy.backend.usecase.integration.invitations.cancel.UsecaseCancelInvitationRepository
import app.meetacy.backend.usecase.invitations.cancel.CancelInvitationUsecase
import app.meetacy.backend.usecase.types.AuthRepository
import org.jetbrains.exposed.sql.Database

fun cancelInvitationRepository(db: Database, authRepository: AuthRepository = authRepository(db)): CancelInvitationRepository = UsecaseCancelInvitationRepository(
    usecase = CancelInvitationUsecase(
        authRepository = authRepository,
        storage = DatabaseCancelInvitationStorage(db)
    )
)
