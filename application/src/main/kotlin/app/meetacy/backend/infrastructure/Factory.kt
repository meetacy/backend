package app.meetacy.backend.infrastructure

import app.meetacy.backend.endpoint.prepareEndpoints
import app.meetacy.backend.infrastructure.integration.auth.authDependencies
import app.meetacy.backend.infrastructure.integration.files.filesDependencies
import app.meetacy.backend.infrastructure.integration.friends.friendsDependencies
import app.meetacy.backend.infrastructure.integration.invitations.invitationsDependencies
import app.meetacy.backend.infrastructure.integration.meetings.meetingsDependencies
import app.meetacy.backend.infrastructure.integration.notifications.notificationsDependencies
import app.meetacy.backend.infrastructure.integration.updates.updatesDependencies
import app.meetacy.backend.infrastructure.integration.users.usersDependencies
import app.meetacy.backend.infrastructure.integration.users.validate.validateUsernameRepository
import app.meetacy.di.DI

fun prepareEndpoints(di: DI) = prepareEndpoints(
    port = di.port,
    friendsDependencies = di.friendsDependencies,
    meetingsDependencies = di.meetingsDependencies,
    notificationsDependencies = di.notificationsDependencies,
    filesDependencies = di.filesDependencies,
    usersDependencies = di.usersDependencies,
    invitationsDependencies = di.invitationsDependencies,
    updatesDependencies = di.updatesDependencies,
    validateUsernameRepository = di.validateUsernameRepository
)
