package app.meetacy.backend.infrastructure.usecase.meetings.edit

import app.meetacy.backend.feature.meetings.endpoints.edit.EditMeetingRepository
import app.meetacy.backend.infrastructure.database.files.filesRepository
import app.meetacy.backend.infrastructure.database.meetings.edit.editMeetingStorage
import app.meetacy.backend.infrastructure.database.meetings.view.viewMeetingRepository
import app.meetacy.backend.infrastructure.database.meetings.get.getMeetingViewRepository
import app.meetacy.backend.feature.meetings.usecase.integration.edit.UsecaseEditMeetingRepository
import app.meetacy.backend.feature.meetings.usecase.edit.EditMeetingUsecase
import app.meetacy.backend.types.integration.utf8Checker.DefaultUtf8Checker
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.editMeetingRepository: EditMeetingRepository by Dependency

fun DIBuilder.editMeetingRepository() {
    val editMeetingRepository by singleton<EditMeetingRepository> {
        UsecaseEditMeetingRepository(
            usecase = EditMeetingUsecase(
                editMeetingStorage,
                authRepository = get(),
                getMeetingViewRepository,
                viewMeetingRepository,
                filesRepository,
                DefaultUtf8Checker
            )
        )
    }
}
