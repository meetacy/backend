@file:Suppress("UNUSED_VARIABLE")

package app.meetacy.backend.infrastructure.integrations.meetings.edit

import app.meetacy.backend.database.integration.meetings.edit.DatabaseEditMeetingStorage
import app.meetacy.backend.endpoint.meetings.edit.EditMeetingRepository
import app.meetacy.backend.infrastructure.database.auth.authRepository
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.backend.infrastructure.database.files.filesRepository
import app.meetacy.backend.infrastructure.integrations.meetings.create.viewMeetingRepository
import app.meetacy.backend.infrastructure.integrations.meetings.get.getMeetingViewRepository
import app.meetacy.backend.usecase.integration.meetings.edit.UsecaseEditMeetingRepository
import app.meetacy.backend.usecase.meetings.edit.EditMeetingUsecase
import app.meetacy.backend.utf8.integration.DefaultUtf8Checker
import app.meetacy.di.DI
import app.meetacy.di.builder.DIBuilder
import app.meetacy.di.dependency.Dependency

val DI.editMeetingRepository: EditMeetingRepository by Dependency

fun DIBuilder.editMeetingRepository() {
    val editMeetingRepository by singleton<EditMeetingRepository> {
        UsecaseEditMeetingRepository(
            usecase = EditMeetingUsecase(
                DatabaseEditMeetingStorage(database),
                authRepository,
                getMeetingViewRepository,
                viewMeetingRepository,
                filesRepository,
                DefaultUtf8Checker
            )
        )
    }
}