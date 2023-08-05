package app.meetacy.backend.infrastructure.integration

import app.meetacy.backend.infrastructure.integration.auth.auth
import app.meetacy.backend.infrastructure.integration.files.files
import app.meetacy.backend.infrastructure.integration.friends.friends
import app.meetacy.backend.infrastructure.integration.invitations.invitations
import app.meetacy.backend.infrastructure.integration.meetings.meetings
import app.meetacy.backend.infrastructure.integration.notifications.notifications
import app.meetacy.backend.infrastructure.integration.updates.updates
import app.meetacy.backend.infrastructure.integration.users.users
import app.meetacy.backend.types.common
import app.meetacy.di.builder.DIBuilder

fun DIBuilder.integrations() {
    auth()
    common()
    files()
    friends()
    invitations()
    meetings()
    notifications()
    users()
    updates()
}
