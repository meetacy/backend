package app.meetacy.backend.types.integration

import app.meetacy.backend.types.integration.auth.authRepository
import app.meetacy.backend.types.integration.files.filesRepository
import app.meetacy.backend.types.integration.generator.accessHashGenerator
import app.meetacy.backend.types.integration.meetings.meetings
import app.meetacy.backend.types.integration.notification.notification
import app.meetacy.backend.types.integration.users.getUsersViewsRepository
import app.meetacy.backend.types.integration.utf8Checker.utf8Checker
import app.meetacy.di.builder.DIBuilder

fun DIBuilder.types() {
    authRepository()
    filesRepository()
    accessHashGenerator()
    meetings()
    notification()
    getUsersViewsRepository()
    utf8Checker()
}
