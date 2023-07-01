package app.meetacy.backend.infrastructure

import app.meetacy.backend.database.integration.meetings.DatabaseCheckMeetingsViewRepository
import app.meetacy.backend.database.integration.meetings.get.DatabaseGetMeetingsViewsViewMeetingsRepository
import app.meetacy.backend.database.integration.notifications.AddNotificationUsecase
import app.meetacy.backend.database.integration.types.*
import app.meetacy.backend.database.integration.updates.stream.UpdatesMiddleware
import app.meetacy.backend.endpoint.startEndpoints
import app.meetacy.backend.infrastructure.factories.*
import app.meetacy.backend.infrastructure.factories.auth.authDependenciesFactory
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
            db, authRepository, getMeetingsViewsRepository,
            getUsersViewsRepository
        ),
        filesDependencies = fileDependenciesFactory(db, authRepository, filesBasePath, filesLimit),
        invitationsDependencies = invitationDependenciesFactory(db, addNotificationUsecase, authRepository, getInvitationsViewsRepository),
        updatesDependencies = updatesDependenciesFactory(db, authRepository, getNotificationsViewsRepository, updatesMiddleware)
    )
}
