package app.meetacy.backend.infrastructure

import app.meetacy.backend.endpoint.startEndpoints
import app.meetacy.backend.infrastructure.factories.auth.authDependenciesFactory
import app.meetacy.backend.infrastructure.factories.files.fileDependenciesFactory
import app.meetacy.backend.infrastructure.factories.friends.friendDependenciesFactory
import app.meetacy.backend.infrastructure.factories.invitations.invitationDependenciesFactory
import app.meetacy.backend.infrastructure.factories.meetings.meetingsDependenciesFactory
import app.meetacy.backend.infrastructure.factories.notifications.notificationDependenciesFactory
import app.meetacy.backend.infrastructure.factories.updates.updatesDependenciesFactory
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
        authDependencies = authDependenciesFactory(db),
        usersDependencies = userDependenciesFactory(db),
        friendsDependencies = friendDependenciesFactory(db),
        meetingsDependencies = meetingsDependenciesFactory(db),
        notificationsDependencies = notificationDependenciesFactory(db),
        filesDependencies = fileDependenciesFactory(db, filesBasePath, filesLimit),
        invitationsDependencies = invitationDependenciesFactory(db),
        updatesDependencies = updatesDependenciesFactory(db)
    )
}
