@file:Suppress("UNUSED_VARIABLE", "NAME_SHADOWING")

package app.meetacy.backend.infrastructure

import app.meetacy.backend.di.builder.di
import app.meetacy.backend.endpoint.startEndpoints
import org.jetbrains.exposed.sql.Database

fun startEndpoints(
    filesBasePath: String,
    filesLimit: Long,
    port: Int,
    db: Database,
    wait: Boolean,
) {
    val di = di() extend {
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
