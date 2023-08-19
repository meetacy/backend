package app.meetacy.backend.application.database.meetings.history

import app.meetacy.feature.meetings.database.integration.meetings.history.list.DatabaseListMeetingsHistoryListStorage
import app.meetacy.backend.application.database.database
import app.meetacy.backend.application.database.meetings.history.active.activeMeetings
import app.meetacy.backend.application.database.meetings.history.past.pastMeetings
import app.meetacy.backend.feature.meetings.usecase.history.list.ListMeetingsHistoryUsecase
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
