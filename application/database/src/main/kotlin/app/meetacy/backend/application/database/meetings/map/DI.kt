package app.meetacy.backend.application.database.meetings.map

import app.meetacy.feature.meetings.database.integration.meetings.map.list.DatabaseListMeetingsMapListStorage
import app.meetacy.backend.application.database.database
import app.meetacy.backend.feature.meetings.usecase.map.list.ListMeetingsMapUsecase
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.listMeetingsMapListStorage: ListMeetingsMapUsecase.Storage by Dependency

fun DIBuilder.meetingMap() {
    val listMeetingsMapListStorage by singleton<ListMeetingsMapUsecase.Storage> {
        DatabaseListMeetingsMapListStorage(database)
    }
}
