package app.meetacy.backend.infrastructure

import app.meetacy.backend.di.DI
import app.meetacy.backend.endpoint.startEndpoints
import app.meetacy.backend.infrastructure.integrations.auth.authDependencies
import app.meetacy.backend.infrastructure.integrations.files.filesDependencies
import app.meetacy.backend.infrastructure.integrations.friends.friendsDependencies
import app.meetacy.backend.infrastructure.integrations.invitations.invitationsDependencies
import app.meetacy.backend.infrastructure.integrations.meetings.meetingsDependencies
import app.meetacy.backend.infrastructure.integrations.notifications.notificationsDependencies
import app.meetacy.backend.infrastructure.integrations.users.usersDependencies

fun startEndpoints(
    di: DI,
    wait: Boolean,
) {
    startEndpoints(
        port = di.port,
        wait = wait,
        authDependencies = di.authDependencies,
        usersDependencies = di.usersDependencies,
        friendsDependencies = di.friendsDependencies,
        meetingsDependencies = di.meetingsDependencies,
        notificationsDependencies = di.notificationsDependencies,
        filesDependencies = di.filesDependencies,
        invitationsDependencies = di.invitationsDependencies,
        updatesDependencies = di.get()
    )
}
