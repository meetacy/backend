package app.meetacy.backend.feature.meetings.usecase.integration.leave

import app.meetacy.backend.feature.meetings.database.meetings.ParticipantsStorage
import app.meetacy.backend.feature.meetings.usecase.leave.LeaveMeetingUsecase
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.types.meetings.GetMeetingsViewsRepository
import app.meetacy.backend.types.meetings.MeetingId
import app.meetacy.backend.types.users.UserId
import app.meetacy.di.builder.DIBuilder

internal fun DIBuilder.leaveMeetingUsecase() {
    val leaveMeetingUsecase by singleton {
        val authRepository: AuthRepository by getting
        val getMeetingsViewsRepository: GetMeetingsViewsRepository by getting

        val participantsStorage: ParticipantsStorage by getting

        val storage = object : LeaveMeetingUsecase.Storage {
            override suspend fun leaveMeeting(meetingId: MeetingId, userId: UserId) {
                participantsStorage.leaveMeeting(meetingId, userId)
            }
        }

        LeaveMeetingUsecase(
            authRepository = authRepository,
            getMeetingsViewsRepository = getMeetingsViewsRepository,
            storage = storage
        )
    }
}
