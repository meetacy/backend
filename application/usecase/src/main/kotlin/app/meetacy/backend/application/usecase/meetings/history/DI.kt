package app.meetacy.backend.application.usecase.meetings.history

import app.meetacy.backend.feature.meetings.endpoints.history.list.ListMeetingsHistoryRepository
import app.meetacy.backend.infrastructure.database.meetings.get.getMeetingViewRepository
import app.meetacy.backend.infrastructure.database.meetings.history.listMeetingsHistoryStorage
import app.meetacy.backend.infrastructure.usecase.meetings.history.active.listActiveMeetingsRepository
import app.meetacy.backend.infrastructure.usecase.meetings.history.past.listPastMeetingsRepository
import app.meetacy.backend.feature.meetings.usecase.integration.history.list.UsecaseListMeetingsHistoryRepository
import app.meetacy.backend.feature.meetings.usecase.history.list.ListMeetingsHistoryUsecase
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.listMeetingsHistoryRepository: ListMeetingsHistoryRepository by Dependency

fun DIBuilder.meetingsHistoryDependencies() {
    listActiveMeetingsRepository()
    listPastMeetingsRepository()
    val listMeetingsHistoryRepository by singleton<ListMeetingsHistoryRepository> {
        UsecaseListMeetingsHistoryRepository(
            usecase = ListMeetingsHistoryUsecase(
                authRepository = get(),
                listMeetingsHistoryStorage,
                getMeetingViewRepository
            )
        )
    }
}
