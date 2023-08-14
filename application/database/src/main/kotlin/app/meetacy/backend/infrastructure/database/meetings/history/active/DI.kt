package app.meetacy.backend.infrastructure.database.meetings.history.active

import app.meetacy.backend.database.integration.meetings.history.active.DatabaseListActiveMeetingsStorage
import app.meetacy.backend.feature.auth.usecase.meetings.history.active.ListMeetingsActiveUsecase
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.listActiveMeetingsStorage: ListMeetingsActiveUsecase.Storage by Dependency

fun DIBuilder.activeMeetings() {
    val listActiveMeetingsStorage by singleton<ListMeetingsActiveUsecase.Storage> {
        DatabaseListActiveMeetingsStorage(database)
    }
}
