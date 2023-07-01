package app.meetacy.backend.infrastructure.factories.invitations

import app.meetacy.backend.database.integration.invitations.create.DatabaseCreateInvitationStorage
import app.meetacy.backend.database.integration.invitations.deny.DatabaseDenyInvitationStorage
import app.meetacy.backend.endpoint.invitations.InvitationsDependencies
import app.meetacy.backend.hash.integration.DefaultHashGenerator
import app.meetacy.backend.infrastructure.factories.auth.authRepository
import app.meetacy.backend.infrastructure.factories.invitations.accept.acceptInvitationRepository
import app.meetacy.backend.infrastructure.factories.invitations.cancel.cancelInvitationRepository
import app.meetacy.backend.infrastructure.factories.notifications.addNotificationUsecase
import app.meetacy.backend.usecase.integration.invitations.create.UsecaseCreateInvitationRepository
import app.meetacy.backend.usecase.integration.invitations.deny.UsecaseDenyInvitationRepository
import app.meetacy.backend.usecase.invitations.add.AddNotificationUsecase
import app.meetacy.backend.usecase.invitations.create.CreateInvitationUsecase
import app.meetacy.backend.usecase.invitations.deny.DenyInvitationUsecase
import app.meetacy.backend.usecase.types.AuthRepository
import app.meetacy.backend.usecase.types.GetInvitationsViewsRepository
import org.jetbrains.exposed.sql.Database

fun invitationDependenciesFactory(
    db: Database,
    addNotificationUsecase: AddNotificationUsecase = addNotificationUsecase(db),
    authRepository: AuthRepository = authRepository(db),
    getInvitationsViewsRepository: GetInvitationsViewsRepository = getInvitationsViewsRepository(db)
): InvitationsDependencies = InvitationsDependencies(
    invitationsCreateRepository = UsecaseCreateInvitationRepository(
        usecase = CreateInvitationUsecase(
            authRepository = authRepository,
            storage = DatabaseCreateInvitationStorage(db, addNotificationUsecase),
            hashGenerator = DefaultHashGenerator,
            invitationsRepository = getInvitationsViewsRepository
        )
    ),
    invitationsAcceptRepository = acceptInvitationRepository(db),
    invitationsDenyRepository = UsecaseDenyInvitationRepository(
        usecase = DenyInvitationUsecase(
            authRepository = authRepository,
            storage = DatabaseDenyInvitationStorage(db)
        )
    ),
    invitationCancelRepository = cancelInvitationRepository(db)
)
