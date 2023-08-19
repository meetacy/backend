package app.meetacy.backend.application.database.meetings.delete

import app.meetacy.feature.meetings.database.integration.meetings.delete.DatabaseDeleteMeetingStorage
import app.meetacy.backend.application.database.database
import app.meetacy.backend.feature.meetings.usecase.delete.DeleteMeetingUsecase
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.deleteMeetingStorage: DeleteMeetingUsecase.Storage by Dependency

fun DIBuilder.deleteMeeting() {
    val deleteMeetingStorage by singleton<DeleteMeetingUsecase.Storage> {
        DatabaseDeleteMeetingStorage(database)
    }
}
