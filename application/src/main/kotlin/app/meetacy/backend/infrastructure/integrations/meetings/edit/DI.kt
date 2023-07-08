@file:Suppress("UNUSED_VARIABLE")

package app.meetacy.backend.infrastructure.integrations.meetings.edit

import app.meetacy.backend.database.integration.meetings.edit.DatabaseEditMeetingStorage
import app.meetacy.backend.di.DI
import app.meetacy.backend.di.builder.DIBuilder
import app.meetacy.backend.di.dependency.Dependency
import app.meetacy.backend.endpoint.meetings.edit.EditMeetingRepository
import app.meetacy.backend.infrastructure.database.database
import app.meetacy.backend.infrastructure.integrations.auth.authRepository
import app.meetacy.backend.infrastructure.integrations.files.filesRepository
import app.meetacy.backend.infrastructure.integrations.meetings.create.viewMeetingRepository
import app.meetacy.backend.infrastructure.integrations.meetings.get.getMeetingsViewsRepository
import app.meetacy.backend.usecase.integration.meetings.edit.UsecaseEditMeetingRepository
import app.meetacy.backend.usecase.meetings.edit.EditMeetingUsecase
import app.meetacy.backend.utf8.integration.DefaultUtf8Checker

val DI.editMeetingRepository: EditMeetingRepository by Dependency

fun DIBuilder.editMeetingRepository() {
    val editMeetingRepository by singleton<EditMeetingRepository> {
        UsecaseEditMeetingRepository(
            usecase = EditMeetingUsecase(
                DatabaseEditMeetingStorage(database),
                authRepository,
                getMeetingsViewsRepository(database),
                viewMeetingRepository,
                filesRepository,
                DefaultUtf8Checker
            )
        )
    }
}