@file:Suppress("UNUSED_VARIABLE")

package app.meetacy.backend.infrastructure.integrations.meetings.delete

import app.meetacy.backend.database.integration.meetings.delete.DatabaseDeleteMeetingStorage
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency
import app.meetacy.backend.endpoint.meetings.delete.DeleteMeetingRepository
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.backend.infrastructure.integrations.auth.authRepository
import app.meetacy.backend.infrastructure.integrations.meetings.get.getMeetingsViewsRepository
import app.meetacy.backend.usecase.integration.meetings.delete.UsecaseDeleteMeetingRepository
import app.meetacy.backend.usecase.meetings.delete.DeleteMeetingUsecase

val DI.deleteMeetingRepository: DeleteMeetingRepository by Dependency

fun DIBuilder.deleteMeetingRepository() {
    val deleteMeetingRepository by singleton<DeleteMeetingRepository> {
        UsecaseDeleteMeetingRepository(
            usecase = DeleteMeetingUsecase(
                authRepository = authRepository,
                getMeetingsViewsRepository = getMeetingsViewsRepository(database),
                storage = DatabaseDeleteMeetingStorage(database)
            )
        )
    }
}
