package app.meetacy.backend.application.usecase.meetings.delete

import app.meetacy.backend.feature.meetings.endpoints.delete.DeleteMeetingRepository
import app.meetacy.backend.application.database.meetings.delete.deleteMeetingStorage
import app.meetacy.backend.application.database.meetings.get.getMeetingViewRepository
import app.meetacy.backend.feature.meetings.usecase.integration.delete.UsecaseDeleteMeetingRepository
import app.meetacy.backend.feature.meetings.usecase.delete.DeleteMeetingUsecase
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.deleteMeetingRepository: DeleteMeetingRepository by Dependency

fun DIBuilder.deleteMeetingRepository() {
    val deleteMeetingRepository by singleton<DeleteMeetingRepository> {
        UsecaseDeleteMeetingRepository(
            usecase = DeleteMeetingUsecase(
                authRepository = get(),
                getMeetingsViewsRepository = getMeetingViewRepository,
                storage = deleteMeetingStorage
            )
        )
    }
}
