package app.meetacy.backend.infrastructure.integrations

import app.meetacy.di.builder.DIBuilder
import app.meetacy.backend.infrastructure.integrations.auth.auth
import app.meetacy.backend.infrastructure.integrations.files.files
import app.meetacy.backend.infrastructure.integrations.friends.friends
import app.meetacy.backend.infrastructure.integrations.invitations.invitations
import app.meetacy.backend.infrastructure.integrations.meetings.meetings
import app.meetacy.backend.infrastructure.integrations.notifications.notifications
import app.meetacy.backend.infrastructure.integrations.updates.updates
import app.meetacy.backend.infrastructure.integrations.users.users

fun DIBuilder.integrations() {
    auth()
    files()
    friends()
    invitations()
    meetings()
    notifications()
    users()
    updates()
}
