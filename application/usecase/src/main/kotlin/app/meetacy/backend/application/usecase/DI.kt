package app.meetacy.backend.application.usecase

import app.meetacy.backend.feature.auth.usecase.integration.auth
import app.meetacy.backend.feature.email.usecase.integration.email
import app.meetacy.backend.feature.files.usecase.integration.files
import app.meetacy.backend.feature.friends.usecase.integration.friends
import app.meetacy.backend.feature.invitations.usecase.integration.invitations
import app.meetacy.backend.feature.meetings.usecase.integration.meetings
import app.meetacy.backend.feature.notifications.usecase.integration.notification
import app.meetacy.backend.feature.updates.usecase.integration.updates
import app.meetacy.backend.feature.users.usecase.integration.users
import app.meetacy.di.builder.DIBuilder

fun DIBuilder.usecase() {
    auth()
    email()
    files()
    friends()
    invitations()
    updates()
    meetings()
    notification()
    users()
}
