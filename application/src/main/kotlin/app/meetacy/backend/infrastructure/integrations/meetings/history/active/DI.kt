@file:Suppress("UNUSED_VARIABLE")

package app.meetacy.backend.infrastructure.integrations.meetings.history.active

import app.meetacy.backend.database.integration.meetings.history.active.DatabaseListActiveMeetingsStorage
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency
import app.meetacy.backend.endpoint.meetings.history.active.ListMeetingsActiveRepository
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.backend.infrastructure.integrations.auth.authRepository
import app.meetacy.backend.infrastructure.integrations.meetings.get.getMeetingsViewsRepository
import app.meetacy.backend.usecase.integration.meetings.history.active.UsecaseListActiveMeetingsRepository
import app.meetacy.backend.usecase.meetings.history.active.ListMeetingsActiveUsecase

val DI.listActiveMeetingsRepository: ListMeetingsActiveRepository by Dependency

fun DIBuilder.listActiveMeetingsRepository() {
    val listActiveMeetingsRepository by singleton<ListMeetingsActiveRepository> {
        UsecaseListActiveMeetingsRepository(
            usecase = ListMeetingsActiveUsecase(
                authRepository,
                DatabaseListActiveMeetingsStorage(database),
                getMeetingsViewsRepository(database)
            )
        )
    }
}
