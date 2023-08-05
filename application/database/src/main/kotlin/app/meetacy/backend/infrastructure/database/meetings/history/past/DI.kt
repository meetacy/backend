package app.meetacy.backend.infrastructure.database.meetings.history.past

import app.meetacy.backend.database.integration.meetings.history.past.DatabaseListPastMeetingsStorage
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.backend.usecase.meetings.history.past.ListMeetingsPastUsecase
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.listPastMeetingsStorage: ListMeetingsPastUsecase.Storage by Dependency

fun DIBuilder.pastMeetings() {
    val listPastMeetingsStorage by singleton<ListMeetingsPastUsecase.Storage> {
        DatabaseListPastMeetingsStorage(database)
    }
}
