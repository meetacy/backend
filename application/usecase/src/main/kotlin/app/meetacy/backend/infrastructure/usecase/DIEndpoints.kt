package app.meetacy.backend.infrastructure.usecase

import app.meetacy.backend.feature.auth.endpoints.integration.auth
import app.meetacy.backend.infrastructure.usecase.email.email
import app.meetacy.backend.infrastructure.usecase.files.files
import app.meetacy.backend.infrastructure.usecase.friends.friends
import app.meetacy.backend.infrastructure.usecase.invitations.invitations
import app.meetacy.backend.infrastructure.usecase.meetings.meetings
import app.meetacy.backend.infrastructure.usecase.notifications.notifications
import app.meetacy.backend.infrastructure.usecase.updates.updates
import app.meetacy.backend.infrastructure.usecase.users.users
import app.meetacy.backend.types.integration.common
import app.meetacy.di.builder.DIBuilder

fun DIBuilder.endpoints() {
    auth()
    // fixme: migrate all storages below
    email()
    common()
    files()
    friends()
    invitations()
    meetings()
    notifications()
    users()
    updates()
}
