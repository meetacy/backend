package app.meetacy.backend.feature.meetings.usecase.integration.delete

import app.meetacy.backend.feature.meetings.database.meetings.MeetingsStorage
import app.meetacy.backend.feature.meetings.usecase.delete.DeleteMeetingUsecase
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.types.meetings.GetMeetingsViewsRepository
import app.meetacy.backend.types.meetings.MeetingId
import app.meetacy.di.builder.DIBuilder

internal fun DIBuilder.deleteMeetingUsecase() {
    val deleteMeetingUsecase by singleton {
        val authRepository: AuthRepository by getting
        val getMeetingsViewsRepository: GetMeetingsViewsRepository by getting

        val meetingsStorage: MeetingsStorage by getting

        val storage = object : DeleteMeetingUsecase.Storage {
            override suspend fun deleteMeeting(meetingId: MeetingId) {
                meetingsStorage.deleteMeeting(meetingId)
            }
        }

        DeleteMeetingUsecase(
            authRepository = authRepository,
            getMeetingsViewsRepository = getMeetingsViewsRepository,
            storage = storage
        )
    }
}
