package app.meetacy.backend.infrastructure

import app.meetacy.backend.database.integration.files.DatabaseGetFileRepository
import app.meetacy.backend.database.integration.files.DatabaseUploadFileStorage
import app.meetacy.backend.database.integration.invitations.accept.DatabaseAcceptInvitationStorage
import app.meetacy.backend.database.integration.invitations.cancel.DatabaseCancelInvitationStorage
import app.meetacy.backend.database.integration.invitations.create.DatabaseCreateInvitationStorage
import app.meetacy.backend.database.integration.invitations.deny.DatabaseDenyInvitationStorage
import app.meetacy.backend.database.integration.meetings.DatabaseCheckMeetingsViewRepository
import app.meetacy.backend.database.integration.meetings.get.DatabaseGetMeetingsViewsViewMeetingsRepository
import app.meetacy.backend.database.integration.notifications.AddNotificationUsecase
import app.meetacy.backend.database.integration.types.*
import app.meetacy.backend.database.integration.updates.stream.StreamUpdatesUsecase
import app.meetacy.backend.database.integration.updates.stream.UpdatesMiddleware
import app.meetacy.backend.endpoint.files.FilesDependencies
import app.meetacy.backend.endpoint.invitations.InvitationsDependencies
import app.meetacy.backend.endpoint.startEndpoints
import app.meetacy.backend.endpoint.updates.UpdatesDependencies
import app.meetacy.backend.hash.integration.DefaultHashGenerator
import app.meetacy.backend.infrastructure.factories.*
import app.meetacy.backend.usecase.files.UploadFileUsecase
import app.meetacy.backend.usecase.integration.files.UsecaseUploadFileRepository
import app.meetacy.backend.usecase.integration.invitations.accept.UsecaseAcceptInvitationRepository
import app.meetacy.backend.usecase.integration.invitations.cancel.UsecaseCancelInvitationRepository
import app.meetacy.backend.usecase.integration.invitations.create.UsecaseCreateInvitationRepository
import app.meetacy.backend.usecase.integration.invitations.deny.UsecaseDenyInvitationRepository
import app.meetacy.backend.usecase.integration.updates.stream.UsecaseStreamUpdatesRepository
import app.meetacy.backend.usecase.invitations.accept.AcceptInvitationUsecase
import app.meetacy.backend.usecase.invitations.cancel.CancelInvitationUsecase
import app.meetacy.backend.usecase.invitations.create.CreateInvitationUsecase
import app.meetacy.backend.usecase.invitations.deny.DenyInvitationUsecase
import org.jetbrains.exposed.sql.Database

fun startEndpoints(
    filesBasePath: String,
    filesLimit: Long,
    port: Int,
    db: Database,
    wait: Boolean,
) {
    val authRepository = DatabaseAuthRepository(db)
    val filesRepository = DatabaseFilesRepository(db)

    val getUsersViewsRepository = DatabaseGetUsersViewsRepository(db)
    val getMeetingsViewsRepository = DatabaseGetMeetingsViewsRepository(db)
    val viewMeetingsRepository = DatabaseGetMeetingsViewsViewMeetingsRepository(db)
    val checkMeetingsRepository = DatabaseCheckMeetingsViewRepository(db)
    val getInvitationsViewsRepository = GetInvitationsViewsRepository(
        db,
        viewInvitationsRepository = ViewInvitationsRepository(
            usersRepository = getUsersViewsRepository,
            meetingsRepository = getMeetingsViewsRepository
        )
    )
    val getNotificationsViewsRepository = GetNotificationsViewsRepository(
        db = db,
        getMeetingsViewsRepository = getMeetingsViewsRepository,
        getUsersViewsRepository = getUsersViewsRepository
    )

    val updatesMiddleware = UpdatesMiddleware(db)
    val addNotificationUsecase = AddNotificationUsecase(db, updatesMiddleware)

    startEndpoints(
        port = port,
        wait = wait,
        authDependencies = authDependenciesFactory(db, authRepository),
        usersDependencies = userDependenciesFactory(db, authRepository, filesRepository, getUsersViewsRepository),
        friendsDependencies = friendDependenciesFactory(db, addNotificationUsecase, authRepository, getUsersViewsRepository),
        meetingsDependencies = meetingsDependenciesFactory(
            db, authRepository, filesRepository, checkMeetingsRepository,
            getMeetingsViewsRepository, getUsersViewsRepository, viewMeetingsRepository
        ),
        notificationsDependencies = notificationDependenciesFactory(
            db, authRepository,
            getMeetingsViewsRepository,
            getUsersViewsRepository
        ),
        filesDependencies = FilesDependencies(
            saveFileRepository = UsecaseUploadFileRepository(
                usecase = UploadFileUsecase(
                    authRepository = authRepository,
                    storage = DatabaseUploadFileStorage(db),
                    hashGenerator = DefaultHashGenerator
                ),
                basePath = filesBasePath,
                filesLimit = filesLimit,
                deleteFilesOnExit = false
            ),
            getFileRepository = DatabaseGetFileRepository(
                database = db,
                basePath = filesBasePath
            )
        ),
        invitationsDependencies = InvitationsDependencies(
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
        ),
        updatesDependencies = UpdatesDependencies(
            streamUpdatesRepository = UsecaseStreamUpdatesRepository(
                usecase = StreamUpdatesUsecase(
                    auth = authRepository,
                    notificationsRepository = getNotificationsViewsRepository,
                    updatesMiddleware = updatesMiddleware
                )
            )
        )
    )
}
