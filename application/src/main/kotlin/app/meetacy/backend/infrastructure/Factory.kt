package app.meetacy.backend.infrastructure

import app.meetacy.backend.di.DI
import app.meetacy.backend.endpoint.startEndpoints
import app.meetacy.backend.infrastructure.integrations.auth.authDependencies
import app.meetacy.backend.infrastructure.integrations.files.filesDependencies

fun startEndpoints(
    di: DI,
    wait: Boolean,
) {
    startEndpoints(
        port = di.port,
        wait = wait,
        authDependencies = di.authDependencies,
        usersDependencies = di.get(),
        friendsDependencies = di.get(),
        meetingsDependencies = di.get(),
        notificationsDependencies = di.get(),
        filesDependencies = di.filesDependencies,
        invitationsDependencies = di.get(),
        updatesDependencies = di.get()
    )
}
