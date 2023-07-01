package app.meetacy.backend.infrastructure

import app.meetacy.backend.database.integration.meetings.DatabaseCheckMeetingsViewRepository
import app.meetacy.backend.database.integration.types.GetInvitationsViewsRepository
import app.meetacy.backend.database.integration.types.GetNotificationsViewsRepository
import app.meetacy.backend.database.integration.types.ViewInvitationsRepository
import app.meetacy.backend.database.integration.updates.stream.UpdatesMiddleware
import app.meetacy.backend.endpoint.startEndpoints
import app.meetacy.backend.infrastructure.factories.auth.authDependenciesFactory
import app.meetacy.backend.infrastructure.factories.files.fileDependenciesFactory
import app.meetacy.backend.infrastructure.factories.friendDependenciesFactory
import app.meetacy.backend.infrastructure.factories.invitationDependenciesFactory
import app.meetacy.backend.infrastructure.factories.meetings.getMeetingsViewsRepository
import app.meetacy.backend.infrastructure.factories.meetings.meetingsDependenciesFactory
import app.meetacy.backend.infrastructure.factories.notifications.notificationDependenciesFactory
import app.meetacy.backend.infrastructure.factories.updatesDependenciesFactory
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
    val checkMeetingsRepository = DatabaseCheckMeetingsViewRepository(db)
    val getInvitationsViewsRepository = GetInvitationsViewsRepository(
        db,
        viewInvitationsRepository = ViewInvitationsRepository(
            usersRepository = getUserViewsRepository(db),
            meetingsRepository = getMeetingsViewsRepository(db)
        )
    )
    val getNotificationsViewsRepository = GetNotificationsViewsRepository(
        db = db,
        getMeetingsViewsRepository = getMeetingsViewsRepository(db),
        getUsersViewsRepository = getUserViewsRepository(db)
    )

    val updatesMiddleware = UpdatesMiddleware(db)

    startEndpoints(
        port = port,
        wait = wait,
        authDependencies = authDependenciesFactory(db = db),
        usersDependencies = userDependenciesFactory(db = db),
        friendsDependencies = friendDependenciesFactory(db = db),
        meetingsDependencies = meetingsDependenciesFactory(
            db = db,
            checkMeetingsRepository = checkMeetingsRepository
        ),
        notificationsDependencies = notificationDependenciesFactory(db = db),
        filesDependencies = fileDependenciesFactory(
            db = db,
            filesBasePath = filesBasePath,
            filesLimit = filesLimit
        ),
        invitationsDependencies = invitationDependenciesFactory(
            db = db,
            getInvitationsViewsRepository = getInvitationsViewsRepository
        ),
        updatesDependencies = updatesDependenciesFactory(
            db = db,
            getNotificationsViewsRepository = getNotificationsViewsRepository,
            updatesMiddleware = updatesMiddleware
        )
    )
}
