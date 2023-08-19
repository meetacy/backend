package app.meetacy.backend.application.usecase

import app.meetacy.backend.feature.auth.endpoints.integration.auth
import app.meetacy.backend.application.usecase.email.email
import app.meetacy.backend.application.usecase.files.files
import app.meetacy.backend.application.usecase.friends.friends
import app.meetacy.backend.application.usecase.invitations.invitations
import app.meetacy.backend.application.usecase.meetings.meetings
import app.meetacy.backend.application.usecase.notifications.notifications
import app.meetacy.backend.application.usecase.updates.updates
import app.meetacy.backend.application.usecase.users.users
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
