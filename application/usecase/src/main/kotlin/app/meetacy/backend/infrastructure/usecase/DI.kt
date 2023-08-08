package app.meetacy.backend.infrastructure.usecase

import app.meetacy.backend.infrastructure.usecase.auth.auth
import app.meetacy.backend.infrastructure.usecase.files.files
import app.meetacy.backend.infrastructure.usecase.friends.friends
import app.meetacy.backend.infrastructure.usecase.invitations.invitations
import app.meetacy.backend.infrastructure.usecase.meetings.meetings
import app.meetacy.backend.infrastructure.usecase.notifications.notifications
import app.meetacy.backend.infrastructure.usecase.updates.updates
import app.meetacy.backend.infrastructure.usecase.users.users
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
