@file:Suppress("UNUSED_VARIABLE")

package app.meetacy.backend.infrastructure.integrations.meetings.history

import app.meetacy.backend.database.integration.meetings.history.list.DatabaseListMeetingsHistoryListStorage
import app.meetacy.backend.endpoint.meetings.history.MeetingsHistoryDependencies
import app.meetacy.backend.endpoint.meetings.history.list.ListMeetingsHistoryRepository
import app.meetacy.backend.infrastructure.database.auth.authRepository
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.backend.infrastructure.integrations.meetings.get.getMeetingViewRepository
import app.meetacy.backend.infrastructure.integrations.meetings.history.active.listActiveMeetingsRepository
import app.meetacy.backend.infrastructure.integrations.meetings.history.past.listPastMeetingsRepository
import app.meetacy.backend.usecase.integration.meetings.history.list.UsecaseListMeetingsHistoryRepository
import app.meetacy.backend.usecase.meetings.history.list.ListMeetingsHistoryUsecase
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.meetingsHistoryDependencies: MeetingsHistoryDependencies by Dependency
val DI.listMeetingsHistoryRepository: ListMeetingsHistoryRepository by Dependency

fun DIBuilder.meetingsHistoryDependencies() {
    listActiveMeetingsRepository()
    listPastMeetingsRepository()
    val meetingsHistoryDependencies by singleton<MeetingsHistoryDependencies> {
        MeetingsHistoryDependencies(
            listMeetingsHistoryRepository,
            listActiveMeetingsRepository,
            listPastMeetingsRepository
        )
    }
    val listMeetingsHistoryRepository by singleton<ListMeetingsHistoryRepository> {
        UsecaseListMeetingsHistoryRepository(
            usecase = ListMeetingsHistoryUsecase(
                authRepository,
                DatabaseListMeetingsHistoryListStorage(
                    database
                ),
                getMeetingViewRepository
            )
        )
    }
}
