package app.meetacy.backend.infrastructure

import app.meetacy.backend.endpoint.prepareEndpoints
import app.meetacy.backend.infrastructure.usecase.friends.friendsDependencies
import app.meetacy.backend.infrastructure.usecase.invitations.invitationsDependencies
import app.meetacy.backend.infrastructure.usecase.notifications.notificationsDependencies
import app.meetacy.backend.infrastructure.usecase.updates.updatesDependencies
import app.meetacy.backend.infrastructure.usecase.users.validate.validateUsernameRepository
import app.meetacy.di.DI

fun prepareEndpoints(di: DI) = prepareEndpoints(
    port = di.port,
    friendsDependencies = di.friendsDependencies,
    notificationsDependencies = di.notificationsDependencies,
    invitationsDependencies = di.invitationsDependencies,
    validateUsernameRepository = di.validateUsernameRepository,
    updatesDependencies = di.updatesDependencies
)
