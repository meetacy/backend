package app.meetacy.backend.infrastructure

import app.meetacy.backend.application.endpoints.prepareEndpoints
import app.meetacy.backend.application.usecase.friends.friendsDependencies
import app.meetacy.backend.application.usecase.invitations.invitationsDependencies
import app.meetacy.backend.application.usecase.notifications.notificationsDependencies
import app.meetacy.backend.application.usecase.updates.updatesDependencies
import app.meetacy.backend.application.usecase.users.validate.validateUsernameRepository
import app.meetacy.di.DI

fun prepareEndpoints(di: DI) = prepareEndpoints(
    port = di.port,
    friendsDependencies = di.friendsDependencies,
    notificationsDependencies = di.notificationsDependencies,
    invitationsDependencies = di.invitationsDependencies,
    validateUsernameRepository = di.validateUsernameRepository,
    updatesDependencies = di.updatesDependencies
)
