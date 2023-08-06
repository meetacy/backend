package app.meetacy.backend.infrastructure.integration.meetings.history

import app.meetacy.backend.endpoint.meetings.history.list.ListMeetingsHistoryRepository
import app.meetacy.backend.infrastructure.database.auth.authRepository
import app.meetacy.backend.infrastructure.database.meetings.get.getMeetingViewRepository
import app.meetacy.backend.infrastructure.database.meetings.history.listMeetingsHistoryStorage
import app.meetacy.backend.infrastructure.integration.meetings.history.active.listActiveMeetingsRepository
import app.meetacy.backend.infrastructure.integration.meetings.history.past.listPastMeetingsRepository
import app.meetacy.backend.usecase.integration.meetings.history.list.UsecaseListMeetingsHistoryRepository
import app.meetacy.backend.usecase.meetings.history.list.ListMeetingsHistoryUsecase
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
                authRepository,
                listMeetingsHistoryStorage,
                getMeetingViewRepository
            )
        )
    }
}
