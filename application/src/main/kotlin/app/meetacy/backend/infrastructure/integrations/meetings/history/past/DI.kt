package app.meetacy.backend.infrastructure.integrations.meetings.history.past

import app.meetacy.backend.endpoint.meetings.history.past.ListMeetingsPastRepository
import app.meetacy.backend.infrastructure.database.auth.authRepository
import app.meetacy.backend.infrastructure.database.meetings.get.getMeetingViewRepository
import app.meetacy.backend.infrastructure.database.meetings.history.past.listPastMeetingsStorage
import app.meetacy.backend.usecase.integration.meetings.history.past.UsecaseListPastMeetingsRepository
import app.meetacy.backend.usecase.meetings.history.past.ListMeetingsPastUsecase
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.listPastMeetingsRepository: ListMeetingsPastRepository by Dependency

fun DIBuilder.listPastMeetingsRepository() {
    val listPastMeetingsRepository by singleton<ListMeetingsPastRepository> {
        UsecaseListPastMeetingsRepository(
            usecase = ListMeetingsPastUsecase(
                authRepository,
                listPastMeetingsStorage,
                getMeetingViewRepository
            )
        )
    }
}
