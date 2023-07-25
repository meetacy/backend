@file:Suppress("UNUSED_VARIABLE")

package app.meetacy.backend.infrastructure.integrations.meetings.create

import app.meetacy.backend.database.integration.meetings.create.DatabaseCreateMeetingStorage
import app.meetacy.backend.database.integration.meetings.create.DatabaseCreateMeetingViewMeetingRepository
import app.meetacy.backend.database.integration.meetings.get.DatabaseGetMeetingsViewsViewMeetingsRepository
import app.meetacy.backend.endpoint.meetings.create.CreateMeetingRepository
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.backend.infrastructure.integrations.auth.authRepository
import app.meetacy.backend.infrastructure.integrations.files.filesRepository
import app.meetacy.backend.usecase.integration.meetings.create.UsecaseCreateMeetingRepository
import app.meetacy.backend.usecase.meetings.create.CreateMeetingUsecase
import app.meetacy.backend.usecase.types.ViewMeetingsRepository
import app.meetacy.backend.utf8.integration.DefaultUtf8Checker
import app.meetacy.di.DI
import app.meetacy.backend.di.accessHashGenerator
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.createMeetingRepository: CreateMeetingRepository by Dependency
val DI.viewMeetingRepository: ViewMeetingsRepository by Dependency

fun DIBuilder.viewMeetingRepository() {
    val viewMeetingRepository by singleton<ViewMeetingsRepository> {
        DatabaseGetMeetingsViewsViewMeetingsRepository(database)
    }
}

fun DIBuilder.createMeetingRepository() {
    viewMeetingRepository()
    val createMeetingRepository by singleton<CreateMeetingRepository> {
        UsecaseCreateMeetingRepository(
            usecase = CreateMeetingUsecase(
                hashGenerator = accessHashGenerator,
                storage = DatabaseCreateMeetingStorage(database),
                authRepository = authRepository,
                viewMeetingRepository = DatabaseCreateMeetingViewMeetingRepository(database),
                utf8Checker = DefaultUtf8Checker,
                filesRepository = filesRepository
            )
        )
    }
}
