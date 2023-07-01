package app.meetacy.backend.infrastructure.factories.invitations

import app.meetacy.backend.database.integration.invitations.deny.DatabaseDenyInvitationStorage
import app.meetacy.backend.endpoint.invitations.InvitationsDependencies
import app.meetacy.backend.infrastructure.factories.auth.authRepository
import app.meetacy.backend.infrastructure.factories.invitations.accept.acceptInvitationRepository
import app.meetacy.backend.infrastructure.factories.invitations.cancel.cancelInvitationRepository
import app.meetacy.backend.infrastructure.factories.invitations.create.createInvitationRepository
import app.meetacy.backend.usecase.integration.invitations.deny.UsecaseDenyInvitationRepository
import app.meetacy.backend.usecase.invitations.deny.DenyInvitationUsecase
import app.meetacy.backend.usecase.types.AuthRepository
import org.jetbrains.exposed.sql.Database

fun invitationDependenciesFactory(
    db: Database,
    authRepository: AuthRepository = authRepository(db)
): InvitationsDependencies = InvitationsDependencies(
    invitationsCreateRepository = createInvitationRepository(db),
    invitationsAcceptRepository = acceptInvitationRepository(db),
    invitationsDenyRepository = UsecaseDenyInvitationRepository(
        usecase = DenyInvitationUsecase(
            authRepository = authRepository,
            storage = DatabaseDenyInvitationStorage(db)
        )
    ),
    invitationCancelRepository = cancelInvitationRepository(db)
)
