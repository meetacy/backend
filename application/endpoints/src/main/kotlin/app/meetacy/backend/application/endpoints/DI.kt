package app.meetacy.backend.application.endpoints

import app.meetacy.backend.application.endpoints.email.email
import app.meetacy.backend.application.endpoints.files.files
import app.meetacy.backend.application.endpoints.friends.friends
import app.meetacy.backend.application.endpoints.invitations.invitations
import app.meetacy.backend.application.endpoints.meetings.meetings
import app.meetacy.backend.application.endpoints.notifications.notifications
import app.meetacy.backend.application.endpoints.updates.updates
import app.meetacy.backend.application.endpoints.users.users
import app.meetacy.backend.feature.auth.endpoints.integration.auth
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
