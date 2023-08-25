package app.meetacy.backend.application.usecase

import app.meetacy.backend.feature.auth.usecase.integration.auth
import app.meetacy.backend.feature.email.usecase.integration.email
import app.meetacy.backend.feature.files.usecase.integration.files
import app.meetacy.backend.feature.meetings.usecase.integration.meetings
import app.meetacy.backend.feature.users.usecase.integration.users
import app.meetacy.di.builder.DIBuilder

fun DIBuilder.usecase() {
    auth()
    email()
    files()
    meetings()
    users()
}
