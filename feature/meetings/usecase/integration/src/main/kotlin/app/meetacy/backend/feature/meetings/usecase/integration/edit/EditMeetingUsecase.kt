package app.meetacy.backend.feature.meetings.usecase.integration.edit

import app.meetacy.backend.feature.meetings.database.meetings.MeetingsStorage
import app.meetacy.backend.feature.meetings.usecase.edit.EditMeetingUsecase
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.types.datetime.Date
import app.meetacy.backend.types.files.FileId
import app.meetacy.backend.types.files.FilesRepository
import app.meetacy.backend.types.location.Location
import app.meetacy.backend.types.meetings.FullMeeting
import app.meetacy.backend.types.meetings.GetMeetingsViewsRepository
import app.meetacy.backend.types.meetings.MeetingId
import app.meetacy.backend.types.meetings.ViewMeetingsRepository
import app.meetacy.backend.types.optional.Optional
import app.meetacy.backend.types.utf8Checker.Utf8Checker
import app.meetacy.di.builder.DIBuilder

internal fun DIBuilder.editMeetingUsecase() {
    val editMeetingUsecase by singleton {
        val authRepository: AuthRepository by getting
        val getMeetingsViewsRepository: GetMeetingsViewsRepository by getting
        val viewMeetingsRepository: ViewMeetingsRepository by getting
        val filesRepository: FilesRepository by getting
        val utf8Checker: Utf8Checker by getting

        val meetingsStorage: MeetingsStorage by getting

        val storage = object : EditMeetingUsecase.Storage {
            override suspend fun editMeeting(
                meetingId: MeetingId,
                avatarId: Optional<FileId?>,
                title: String?,
                description: String?,
                location: Location?,
                date: Date?,
                visibility: FullMeeting.Visibility?
            ) = meetingsStorage.editMeeting(
                meetingId = meetingId,
                avatarId = avatarId,
                title = title,
                description = description,
                location = location,
                date = date,
                visibility = visibility
            )
        }

        EditMeetingUsecase(
            storage = storage,
            authRepository = authRepository,
            getMeetingsViewsRepository = getMeetingsViewsRepository,
            viewMeetingsRepository = viewMeetingsRepository,
            filesRepository = filesRepository,
            utf8Checker = utf8Checker
        )
    }
}
