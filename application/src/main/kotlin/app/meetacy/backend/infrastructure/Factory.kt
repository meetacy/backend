package app.meetacy.backend.infrastructure

import app.meetacy.backend.database.integration.meetings.DatabaseCheckMeetingsViewRepository
import app.meetacy.backend.database.integration.meetings.get.DatabaseGetMeetingsViewsRepository
import app.meetacy.backend.database.integration.meetings.get.DatabaseGetMeetingsViewsViewMeetingsRepository
import app.meetacy.backend.database.integration.types.DatabaseAuthRepository
import app.meetacy.backend.database.integration.types.DatabaseFilesRepository
import app.meetacy.backend.database.integration.types.DatabaseGetInvitationsViewsRepository
import app.meetacy.backend.database.integration.users.get.DatabaseGetUsersViewsRepository
import app.meetacy.backend.endpoint.startEndpoints
import app.meetacy.backend.infrastructure.factories.*
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
        notificationsDependencies = notificationDependenciesFactory(
            db, authRepository, getMeetingsViewsRepository,
            getUsersViewsRepository
        ),
        filesDependencies = fileDependenciesFactory(db, authRepository, filesBasePath, filesLimit),
        invitationsDependencies = invitationDependenciesFactory(db, authRepository, getInvitationsViewsRepository)
    )
}
