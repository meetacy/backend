package app.meetacy.backend.infrastructure.factories.invitations.create

import app.meetacy.backend.database.integration.invitations.create.DatabaseCreateInvitationStorage
import app.meetacy.backend.endpoint.invitations.create.CreateInvitationRepository
import app.meetacy.backend.hash.integration.DefaultHashGenerator
import app.meetacy.backend.infrastructure.factories.auth.authRepository
import app.meetacy.backend.infrastructure.factories.invitations.getInvitationsViewsRepository
import app.meetacy.backend.infrastructure.factories.notifications.addNotificationUsecase
import app.meetacy.backend.usecase.integration.invitations.create.UsecaseCreateInvitationRepository
import app.meetacy.backend.usecase.invitations.add.AddNotificationUsecase
import app.meetacy.backend.usecase.invitations.create.CreateInvitationUsecase
import app.meetacy.backend.usecase.types.AuthRepository
import app.meetacy.backend.usecase.types.GetInvitationsViewsRepository
import org.jetbrains.exposed.sql.Database

fun createInvitationRepository(
    db: Database,
    authRepository: AuthRepository = authRepository(db),
    addNotificationUsecase: AddNotificationUsecase = addNotificationUsecase(db),
    getInvitationsViewsRepository: GetInvitationsViewsRepository = getInvitationsViewsRepository(db)
): CreateInvitationRepository = UsecaseCreateInvitationRepository(
    usecase = CreateInvitationUsecase(
        authRepository = authRepository,
        storage = DatabaseCreateInvitationStorage(db, addNotificationUsecase),
        hashGenerator = DefaultHashGenerator,
        invitationsRepository = getInvitationsViewsRepository
    )
)
