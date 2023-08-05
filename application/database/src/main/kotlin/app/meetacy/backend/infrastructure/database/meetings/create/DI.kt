package app.meetacy.backend.infrastructure.database.meetings.create

import app.meetacy.backend.database.integration.meetings.create.DatabaseCreateMeetingStorage
import app.meetacy.backend.database.integration.meetings.create.DatabaseCreateMeetingViewMeetingRepository
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.backend.usecase.meetings.create.CreateMeetingUsecase
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.createMeetingStorage: CreateMeetingUsecase.Storage by Dependency
val DI.viewMeetingRepository: CreateMeetingUsecase.ViewMeetingRepository by Dependency

fun DIBuilder.createMeeting() {
    val createMeetingStorage by singleton<CreateMeetingUsecase.Storage> {
        DatabaseCreateMeetingStorage(database)
    }
    val viewMeetingRepository by singleton<CreateMeetingUsecase.ViewMeetingRepository> {
        DatabaseCreateMeetingViewMeetingRepository(database)
    }
}
