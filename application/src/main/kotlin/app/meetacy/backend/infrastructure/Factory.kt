@file:Suppress("UNUSED_VARIABLE", "NAME_SHADOWING")

package app.meetacy.backend.infrastructure

import app.meetacy.backend.di.di
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
    val di = di() + di {
        val filesBasePath by constant(filesBasePath)
        val filesLimit by constant(filesLimit)
        val db by constant(db)
    }

    startEndpoints(
        port = port,
        wait = wait,
        authDependencies = di.get(),
        usersDependencies = di.get(),
        friendsDependencies = di.get(),
        meetingsDependencies = di.get(),
        notificationsDependencies = di.get(),
        filesDependencies = di.get(),
        invitationsDependencies = di.get(),
        updatesDependencies = di.get()
    )
}
