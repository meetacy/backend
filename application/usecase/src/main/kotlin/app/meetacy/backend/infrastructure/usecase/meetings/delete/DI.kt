package app.meetacy.backend.infrastructure.usecase.meetings.delete

import app.meetacy.backend.endpoint.meetings.delete.DeleteMeetingRepository
import app.meetacy.backend.feature.auth.usecase.integration.meetings.delete.UsecaseDeleteMeetingRepository
import app.meetacy.backend.feature.auth.usecase.meetings.delete.DeleteMeetingUsecase
import app.meetacy.backend.infrastructure.database.auth.authRepository
import app.meetacy.backend.infrastructure.database.meetings.delete.deleteMeetingStorage
import app.meetacy.backend.infrastructure.database.meetings.get.getMeetingViewRepository
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.deleteMeetingRepository: DeleteMeetingRepository by Dependency

fun DIBuilder.deleteMeetingRepository() {
    val deleteMeetingRepository by singleton<DeleteMeetingRepository> {
        UsecaseDeleteMeetingRepository(
            usecase = DeleteMeetingUsecase(
                authRepository = authRepository,
                getMeetingsViewsRepository = getMeetingViewRepository,
                storage = deleteMeetingStorage
            )
        )
    }
}
