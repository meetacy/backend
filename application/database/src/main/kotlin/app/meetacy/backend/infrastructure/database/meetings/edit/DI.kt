package app.meetacy.backend.infrastructure.database.meetings.edit

import app.meetacy.backend.database.integration.meetings.edit.DatabaseEditMeetingStorage
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.backend.usecase.meetings.edit.EditMeetingUsecase
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.editMeetingStorage: EditMeetingUsecase.Storage by Dependency

fun DIBuilder.editMeeting() {
    val editMeetingStorage by singleton<EditMeetingUsecase.Storage> {
        DatabaseEditMeetingStorage(database)
    }
}
