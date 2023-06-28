package app.meetacy.backend.infrastructure

import app.meetacy.backend.database.integration.files.DatabaseGetFileRepository
import app.meetacy.backend.database.integration.files.DatabaseUploadFileStorage
import app.meetacy.backend.database.integration.invitation.accept.DatabaseAcceptInvitationStorage
import app.meetacy.backend.database.integration.invitation.cancel.DatabaseCancelInvitationStorage
import app.meetacy.backend.database.integration.invitation.create.DatabaseCreateInvitationStorage
import app.meetacy.backend.database.integration.invitation.deny.DatabaseDenyInvitationStorage
import app.meetacy.backend.database.integration.invitation.read.DatabaseReadInvitationStorage
import app.meetacy.backend.database.integration.invitation.update.DatabaseUpdateInvitationStorage
import app.meetacy.backend.database.integration.meetings.DatabaseCheckMeetingsViewRepository
import app.meetacy.backend.database.integration.meetings.get.DatabaseGetMeetingsViewsRepository
import app.meetacy.backend.database.integration.meetings.get.DatabaseGetMeetingsViewsViewMeetingsRepository
import app.meetacy.backend.database.integration.notifications.DatabaseGetNotificationStorage
import app.meetacy.backend.database.integration.notifications.DatabaseReadNotificationsStorage
import app.meetacy.backend.database.integration.types.DatabaseAuthRepository
import app.meetacy.backend.database.integration.types.DatabaseFilesRepository
import app.meetacy.backend.database.integration.types.DatabaseGetInvitationsViewsRepository
import app.meetacy.backend.database.integration.users.get.DatabaseGetUsersViewsRepository
import app.meetacy.backend.endpoint.files.FilesDependencies
import app.meetacy.backend.endpoint.invitations.InvitationsDependencies
import app.meetacy.backend.endpoint.notifications.NotificationsDependencies
import app.meetacy.backend.endpoint.startEndpoints
import app.meetacy.backend.hash.integration.DefaultHashGenerator
import app.meetacy.backend.infrastructure.factories.authDependenciesFactory
import app.meetacy.backend.infrastructure.factories.friendDependenciesFactory
import app.meetacy.backend.infrastructure.factories.meetingsDependenciesFactory
import app.meetacy.backend.infrastructure.factories.userDependenciesFactory
import app.meetacy.backend.usecase.files.UploadFileUsecase
import app.meetacy.backend.usecase.integration.files.UsecaseUploadFileRepository
import app.meetacy.backend.usecase.integration.invitations.accept.UsecaseAcceptInvitationRepository
import app.meetacy.backend.usecase.integration.invitations.cancel.UsecaseCancelInvitationRepository
import app.meetacy.backend.usecase.integration.invitations.create.UsecaseCreateInvitationRepository
import app.meetacy.backend.usecase.integration.invitations.deny.UsecaseDenyInvitationRepository
import app.meetacy.backend.usecase.integration.invitations.read.UsecaseReadInvitationRepository
import app.meetacy.backend.usecase.integration.invitations.update.UsecaseUpdateInvitationRepository
import app.meetacy.backend.usecase.integration.notifications.get.UsecaseGetNotificationsRepository
import app.meetacy.backend.usecase.integration.notifications.read.UsecaseReadNotificationsRepository
import app.meetacy.backend.usecase.invitations.accept.AcceptInvitationUsecase
import app.meetacy.backend.usecase.invitations.cancel.CancelInvitationUsecase
import app.meetacy.backend.usecase.invitations.create.CreateInvitationUsecase
import app.meetacy.backend.usecase.invitations.deny.DenyInvitationUsecase
import app.meetacy.backend.usecase.invitations.read.ReadInvitationUsecase
import app.meetacy.backend.usecase.invitations.update.UpdateInvitationUsecase
import app.meetacy.backend.usecase.notification.GetNotificationsUsecase
import app.meetacy.backend.usecase.notification.ReadNotificationsUsecase
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
    val getInvitationsViewsRepository = DatabaseGetInvitationsViewsRepository(
        getUsersViewsRepository,
        getMeetingsViewsRepository,
        db
    )

    startEndpoints(
        port = port,
        wait = wait,
        authDependencies = authDependenciesFactory(db, authRepository),
        usersDependencies = userDependenciesFactory(db, authRepository, filesRepository, getUsersViewsRepository),
        friendsDependencies = friendDependenciesFactory(db, authRepository, getUsersViewsRepository),
        meetingsDependencies = meetingsDependenciesFactory(
            db, authRepository, filesRepository, checkMeetingsRepository,
            getMeetingsViewsRepository, getUsersViewsRepository, viewMeetingsRepository
        ),
        notificationsDependencies = NotificationsDependencies(
            getNotificationsRepository = UsecaseGetNotificationsRepository(
                usecase = GetNotificationsUsecase(
                    authRepository = authRepository,
                    usersRepository = getUsersViewsRepository,
                    meetingsRepository = getMeetingsViewsRepository,
                    storage = DatabaseGetNotificationStorage(db)
                )
            ),
            readNotificationsRepository = UsecaseReadNotificationsRepository(
                usecase = ReadNotificationsUsecase(
                    authRepository = authRepository,
                    storage = DatabaseReadNotificationsStorage(db)
                )
            )
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
    )
}
