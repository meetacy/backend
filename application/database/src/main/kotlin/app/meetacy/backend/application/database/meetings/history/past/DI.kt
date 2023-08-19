package app.meetacy.backend.application.database.meetings.history.past

import app.meetacy.feature.meetings.database.integration.meetings.history.past.DatabaseListPastMeetingsStorage
import app.meetacy.backend.application.database.database
import app.meetacy.backend.feature.meetings.usecase.history.past.ListMeetingsPastUsecase
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.listPastMeetingsStorage: ListMeetingsPastUsecase.Storage by Dependency

fun DIBuilder.pastMeetings() {
    val listPastMeetingsStorage by singleton<ListMeetingsPastUsecase.Storage> {
        DatabaseListPastMeetingsStorage(database)
    }
}
