package app.meetacy.backend.infrastructure.factories

import app.meetacy.backend.database.integration.invitation.accept.DatabaseAcceptInvitationStorage
import app.meetacy.backend.database.integration.invitation.cancel.DatabaseCancelInvitationStorage
import app.meetacy.backend.database.integration.invitation.create.DatabaseCreateInvitationStorage
import app.meetacy.backend.database.integration.invitation.deny.DatabaseDenyInvitationStorage
import app.meetacy.backend.database.integration.invitation.read.DatabaseReadInvitationStorage
import app.meetacy.backend.database.integration.invitation.update.DatabaseUpdateInvitationStorage
import app.meetacy.backend.endpoint.invitations.InvitationsDependencies
import app.meetacy.backend.hash.integration.DefaultHashGenerator
import app.meetacy.backend.usecase.integration.invitations.accept.UsecaseAcceptInvitationRepository
import app.meetacy.backend.usecase.integration.invitations.cancel.UsecaseCancelInvitationRepository
import app.meetacy.backend.usecase.integration.invitations.create.UsecaseCreateInvitationRepository
import app.meetacy.backend.usecase.integration.invitations.deny.UsecaseDenyInvitationRepository
import app.meetacy.backend.usecase.integration.invitations.read.UsecaseReadInvitationRepository
import app.meetacy.backend.usecase.integration.invitations.update.UsecaseUpdateInvitationRepository
import app.meetacy.backend.usecase.invitations.accept.AcceptInvitationUsecase
import app.meetacy.backend.usecase.invitations.cancel.CancelInvitationUsecase
import app.meetacy.backend.usecase.invitations.create.CreateInvitationUsecase
import app.meetacy.backend.usecase.invitations.deny.DenyInvitationUsecase
import app.meetacy.backend.usecase.invitations.read.ReadInvitationUsecase
import app.meetacy.backend.usecase.invitations.update.UpdateInvitationUsecase
import app.meetacy.backend.usecase.types.AuthRepository
import app.meetacy.backend.usecase.types.GetInvitationsViewsRepository
import org.jetbrains.exposed.sql.Database

fun invitationDependenciesFactory(
    db: Database,
    authRepository: AuthRepository,
    getInvitationsViewsRepository: GetInvitationsViewsRepository
): InvitationsDependencies = InvitationsDependencies(
    invitationsCreateRepository = UsecaseCreateInvitationRepository(
        usecase = CreateInvitationUsecase(
            authRepository = authRepository,
            storage = DatabaseCreateInvitationStorage(db),
            hashGenerator = DefaultHashGenerator,
            getInvitationsViewsRepository = getInvitationsViewsRepository
        )
    ),
    invitationsAcceptRepository = UsecaseAcceptInvitationRepository(
        usecase = AcceptInvitationUsecase(
            authRepository = authRepository,
            storage = DatabaseAcceptInvitationStorage(db)
        )
    ),
    invitationsDenyRepository = UsecaseDenyInvitationRepository(
        usecase = DenyInvitationUsecase(
            authRepository = authRepository,
            storage = DatabaseDenyInvitationStorage(db)
        )
    ),
    invitationsGetRepository = UsecaseReadInvitationRepository(
        usecase = ReadInvitationUsecase(
            storage = DatabaseReadInvitationStorage(db),
            getInvitationsViewsRepository = getInvitationsViewsRepository,
            authRepository = authRepository
        )
    ),
    invitationUpdateRepository = UsecaseUpdateInvitationRepository(
        usecase = UpdateInvitationUsecase(
            storage = DatabaseUpdateInvitationStorage(db),
            authRepository = authRepository,
            getInvitationsViewsRepository = getInvitationsViewsRepository
        )
    ),
    invitationCancelRepository = UsecaseCancelInvitationRepository(
        usecase = CancelInvitationUsecase(
            authRepository = authRepository,
            storage = DatabaseCancelInvitationStorage(db)
        )
    )
)
