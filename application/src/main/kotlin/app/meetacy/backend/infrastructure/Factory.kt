package app.meetacy.backend.infrastructure

import app.meetacy.backend.endpoint.prepareEndpoints
import app.meetacy.backend.infrastructure.integrations.auth.authDependencies
import app.meetacy.backend.infrastructure.integrations.files.filesDependencies
import app.meetacy.backend.infrastructure.integrations.friends.friendsDependencies
import app.meetacy.backend.infrastructure.integrations.invitations.invitationsDependencies
import app.meetacy.backend.infrastructure.integrations.meetings.meetingsDependencies
import app.meetacy.backend.infrastructure.integrations.notifications.notificationsDependencies
import app.meetacy.backend.infrastructure.integrations.updates.updatesDependencies
import app.meetacy.backend.infrastructure.integrations.users.usersDependencies
import app.meetacy.backend.infrastructure.integrations.users.validate.validateUsernameRepository
import app.meetacy.di.DI

fun prepareEndpoints(di: DI) = prepareEndpoints(
    port = di.port,
    authDependencies = di.authDependencies,
    usersDependencies = di.usersDependencies,
    friendsDependencies = di.friendsDependencies,
    meetingsDependencies = di.meetingsDependencies,
    notificationsDependencies = di.notificationsDependencies,
    filesDependencies = di.filesDependencies,
    invitationsDependencies = di.invitationsDependencies,
    updatesDependencies = di.updatesDependencies,
    validateUsernameRepository = di.validateUsernameRepository
)
