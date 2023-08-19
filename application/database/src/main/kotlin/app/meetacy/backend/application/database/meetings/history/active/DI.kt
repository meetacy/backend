package app.meetacy.backend.application.database.meetings.history.active

import app.meetacy.feature.meetings.database.integration.meetings.history.active.DatabaseListActiveMeetingsStorage
import app.meetacy.backend.application.database.database
import app.meetacy.backend.feature.meetings.usecase.history.active.ListMeetingsActiveUsecase
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.listActiveMeetingsStorage: ListMeetingsActiveUsecase.Storage by Dependency

fun DIBuilder.activeMeetings() {
    val listActiveMeetingsStorage by singleton<ListMeetingsActiveUsecase.Storage> {
        DatabaseListActiveMeetingsStorage(database)
    }
}
