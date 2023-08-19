package app.meetacy.backend.application.usecase.meetings.history.past

import app.meetacy.backend.feature.meetings.endpoints.history.past.ListMeetingsPastRepository
import app.meetacy.backend.application.database.meetings.get.getMeetingViewRepository
import app.meetacy.backend.application.database.meetings.history.past.listPastMeetingsStorage
import app.meetacy.backend.feature.meetings.usecase.integration.history.past.UsecaseListPastMeetingsRepository
import app.meetacy.backend.feature.meetings.usecase.history.past.ListMeetingsPastUsecase
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.listPastMeetingsRepository: ListMeetingsPastRepository by Dependency

fun DIBuilder.listPastMeetingsRepository() {
    val listPastMeetingsRepository by singleton<ListMeetingsPastRepository> {
        UsecaseListPastMeetingsRepository(
            usecase = ListMeetingsPastUsecase(
                authRepository = get(),
                listPastMeetingsStorage,
                getMeetingViewRepository
            )
        )
    }
}
