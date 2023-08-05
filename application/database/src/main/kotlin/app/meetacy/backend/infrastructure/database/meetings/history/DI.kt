package app.meetacy.backend.infrastructure.database.meetings.history

import app.meetacy.backend.database.integration.meetings.history.list.DatabaseListMeetingsHistoryListStorage
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.backend.infrastructure.database.meetings.history.active.activeMeetings
import app.meetacy.backend.infrastructure.database.meetings.history.past.pastMeetings
import app.meetacy.backend.usecase.meetings.history.list.ListMeetingsHistoryUsecase
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.listMeetingsHistoryStorage: ListMeetingsHistoryUsecase.Storage by Dependency

fun DIBuilder.meetingsHistory() {
    activeMeetings()
    pastMeetings()
    val listMeetingsHistoryStorage by singleton<ListMeetingsHistoryUsecase.Storage> {
        DatabaseListMeetingsHistoryListStorage(database)
    }
}
