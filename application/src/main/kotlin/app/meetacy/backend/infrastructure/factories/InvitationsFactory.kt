package app.meetacy.backend.infrastructure.factories

import app.meetacy.backend.database.integration.invitations.accept.DatabaseAcceptInvitationStorage
import app.meetacy.backend.database.integration.invitations.cancel.DatabaseCancelInvitationStorage
import app.meetacy.backend.database.integration.invitations.create.DatabaseCreateInvitationStorage
import app.meetacy.backend.database.integration.invitations.deny.DatabaseDenyInvitationStorage
import app.meetacy.backend.endpoint.invitations.InvitationsDependencies
import app.meetacy.backend.hash.integration.DefaultHashGenerator
import app.meetacy.backend.usecase.integration.invitations.accept.UsecaseAcceptInvitationRepository
import app.meetacy.backend.usecase.integration.invitations.cancel.UsecaseCancelInvitationRepository
import app.meetacy.backend.usecase.integration.invitations.create.UsecaseCreateInvitationRepository
import app.meetacy.backend.usecase.integration.invitations.deny.UsecaseDenyInvitationRepository
import app.meetacy.backend.usecase.invitations.accept.AcceptInvitationUsecase
import app.meetacy.backend.usecase.invitations.add.AddNotificationUsecase
import app.meetacy.backend.usecase.invitations.cancel.CancelInvitationUsecase
import app.meetacy.backend.usecase.invitations.create.CreateInvitationUsecase
import app.meetacy.backend.usecase.invitations.deny.DenyInvitationUsecase
import app.meetacy.backend.usecase.types.AuthRepository
import app.meetacy.backend.usecase.types.GetInvitationsViewsRepository
import org.jetbrains.exposed.sql.Database

fun invitationDependenciesFactory(
    db: Database,
    addNotificationUsecase: AddNotificationUsecase,
    authRepository: AuthRepository,
    getInvitationsViewsRepository: GetInvitationsViewsRepository
): InvitationsDependencies = InvitationsDependencies(
    invitationsCreateRepository = UsecaseCreateInvitationRepository(
        usecase = CreateInvitationUsecase(
            authRepository = authRepository,
            storage = DatabaseCreateInvitationStorage(db, addNotificationUsecase),
            hashGenerator = DefaultHashGenerator,
            invitationsRepository = getInvitationsViewsRepository
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
    invitationCancelRepository = UsecaseCancelInvitationRepository(
        usecase = CancelInvitationUsecase(
            authRepository = authRepository,
            storage = DatabaseCancelInvitationStorage(db)
        )
    )
)
