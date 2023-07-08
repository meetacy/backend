@file:Suppress("UNUSED_VARIABLE")

package app.meetacy.backend.infrastructure.integrations.meetings.history.past

import app.meetacy.backend.database.integration.meetings.history.past.DatabaseListPastMeetingsStorage
import app.meetacy.backend.di.DI
import app.meetacy.backend.di.builder.DIBuilder
import app.meetacy.backend.di.dependency.Dependency
import app.meetacy.backend.endpoint.meetings.history.past.ListMeetingsPastRepository
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.backend.infrastructure.integrations.auth.authRepository
import app.meetacy.backend.infrastructure.integrations.meetings.get.getMeetingsViewsRepository
import app.meetacy.backend.usecase.integration.meetings.history.past.UsecaseListPastMeetingsRepository
import app.meetacy.backend.usecase.meetings.history.past.ListMeetingsPastUsecase

val DI.listPastMeetingsRepository: ListMeetingsPastRepository by Dependency

fun DIBuilder.listPastMeetingsRepository() {
    val listPastMeetingsRepository by singleton<ListMeetingsPastRepository> {
        UsecaseListPastMeetingsRepository(
            usecase = ListMeetingsPastUsecase(
                authRepository,
                DatabaseListPastMeetingsStorage(database),
                getMeetingsViewsRepository(database)
            )
        )
    }
}
