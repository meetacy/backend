package app.meetacy.backend.infrastructure.database.meetings.delete

import app.meetacy.backend.database.integration.meetings.delete.DatabaseDeleteMeetingStorage
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.backend.usecase.meetings.delete.DeleteMeetingUsecase
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.deleteMeetingStorage: DeleteMeetingUsecase.Storage by Dependency

fun DIBuilder.deleteMeeting() {
    val deleteMeetingStorage by singleton<DeleteMeetingUsecase.Storage> {
        DatabaseDeleteMeetingStorage(database)
    }
}
