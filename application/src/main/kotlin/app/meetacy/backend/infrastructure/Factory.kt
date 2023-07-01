package app.meetacy.backend.infrastructure

import app.meetacy.backend.database.integration.meetings.DatabaseCheckMeetingsViewRepository
import app.meetacy.backend.database.integration.meetings.get.DatabaseGetMeetingsViewsViewMeetingsRepository
import app.meetacy.backend.database.integration.notifications.AddNotificationUsecase
import app.meetacy.backend.database.integration.types.DatabaseGetMeetingsViewsRepository
import app.meetacy.backend.database.integration.types.GetInvitationsViewsRepository
import app.meetacy.backend.database.integration.types.GetNotificationsViewsRepository
import app.meetacy.backend.database.integration.types.ViewInvitationsRepository
import app.meetacy.backend.database.integration.updates.stream.UpdatesMiddleware
import app.meetacy.backend.endpoint.startEndpoints
import app.meetacy.backend.infrastructure.factories.*
import app.meetacy.backend.infrastructure.factories.auth.authDependenciesFactory
import app.meetacy.backend.infrastructure.factories.files.fileDependenciesFactory
import app.meetacy.backend.infrastructure.factories.users.getUserViewsRepository
import app.meetacy.backend.infrastructure.factories.users.userDependenciesFactory
import org.jetbrains.exposed.sql.Database

fun startEndpoints(
    filesBasePath: String,
    filesLimit: Long,
    port: Int,
    db: Database,
    wait: Boolean,
) {
    val getMeetingsViewsRepository = DatabaseGetMeetingsViewsRepository(db)
    val viewMeetingsRepository = DatabaseGetMeetingsViewsViewMeetingsRepository(db)
    val checkMeetingsRepository = DatabaseCheckMeetingsViewRepository(db)
    val getInvitationsViewsRepository = GetInvitationsViewsRepository(
        db,
        viewInvitationsRepository = ViewInvitationsRepository(
            usersRepository = getUserViewsRepository(db),
            meetingsRepository = getMeetingsViewsRepository
        )
    )
    val getNotificationsViewsRepository = GetNotificationsViewsRepository(
        db = db,
        getMeetingsViewsRepository = getMeetingsViewsRepository,
        getUsersViewsRepository = getUserViewsRepository(db)
    )

    val updatesMiddleware = UpdatesMiddleware(db)
    val addNotificationUsecase = AddNotificationUsecase(db, updatesMiddleware)

    startEndpoints(
        port = port,
        wait = wait,
        authDependencies = authDependenciesFactory(db),
        usersDependencies = userDependenciesFactory(db = db),
        friendsDependencies = friendDependenciesFactory(
            db = db,
            addNotificationUsecase = addNotificationUsecase
        ),
        meetingsDependencies = meetingsDependenciesFactory(
            db = db,
            checkMeetingsRepository = checkMeetingsRepository,
            getMeetingsViewsRepository = getMeetingsViewsRepository,
            viewMeetingsRepository = viewMeetingsRepository
        ),
        notificationsDependencies = notificationDependenciesFactory(
            db = db,
            getMeetingsViewsRepository = getMeetingsViewsRepository
        ),
        filesDependencies = fileDependenciesFactory(
            db = db,
            filesBasePath = filesBasePath,
            filesLimit = filesLimit
        ),
        invitationsDependencies = invitationDependenciesFactory(
            db = db,
            addNotificationUsecase = addNotificationUsecase,
            getInvitationsViewsRepository = getInvitationsViewsRepository
        ),
        updatesDependencies = updatesDependenciesFactory(
            db = db,
            getNotificationsViewsRepository = getNotificationsViewsRepository,
            updatesMiddleware = updatesMiddleware
        )
    )
}
