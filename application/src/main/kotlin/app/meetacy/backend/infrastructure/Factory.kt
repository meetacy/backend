package app.meetacy.backend.infrastructure

import app.meetacy.backend.endpoint.startEndpoints
import app.meetacy.backend.infrastructure.factories.auth.authDependenciesFactory
import app.meetacy.backend.infrastructure.factories.files.fileDependenciesFactory
import app.meetacy.backend.infrastructure.factories.friendDependenciesFactory
import app.meetacy.backend.infrastructure.factories.invitationDependenciesFactory
import app.meetacy.backend.infrastructure.factories.meetings.meetingsDependenciesFactory
import app.meetacy.backend.infrastructure.factories.notifications.notificationDependenciesFactory
import app.meetacy.backend.infrastructure.factories.updatesDependenciesFactory
import app.meetacy.backend.infrastructure.factories.users.userDependenciesFactory
import org.jetbrains.exposed.sql.Database

fun startEndpoints(
    filesBasePath: String,
    filesLimit: Long,
    port: Int,
    db: Database,
    wait: Boolean,
) {
    startEndpoints(
        port = port,
        wait = wait,
        authDependencies = authDependenciesFactory(db = db),
        usersDependencies = userDependenciesFactory(db = db),
        friendsDependencies = friendDependenciesFactory(db = db),
        meetingsDependencies = meetingsDependenciesFactory(db = db),
        notificationsDependencies = notificationDependenciesFactory(db = db),
        filesDependencies = fileDependenciesFactory(
            db = db,
            filesBasePath = filesBasePath,
            filesLimit = filesLimit
        ),
        invitationsDependencies = invitationDependenciesFactory(db = db),
        updatesDependencies = updatesDependenciesFactory(db = db)
    )
}
