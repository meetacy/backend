package app.meetacy.backend.infrastructure.database.meetings.map

import app.meetacy.backend.database.integration.meetings.map.list.DatabaseListMeetingsMapListStorage
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.backend.usecase.meetings.map.list.ListMeetingsMapUsecase
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.listMeetingsMapListStorage: ListMeetingsMapUsecase.Storage by Dependency

fun DIBuilder.meetingMap() {
    val listMeetingsMapListStorage by singleton<ListMeetingsMapUsecase.Storage> {
        DatabaseListMeetingsMapListStorage(database)
    }
}
