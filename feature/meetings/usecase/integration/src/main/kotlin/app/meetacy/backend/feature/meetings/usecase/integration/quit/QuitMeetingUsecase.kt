package app.meetacy.backend.feature.meetings.usecase.integration.quit

import app.meetacy.backend.feature.meetings.database.meetings.ParticipantsStorage
import app.meetacy.backend.feature.meetings.usecase.quit.QuitMeetingUsecase
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.types.meetings.GetMeetingsViewsRepository
import app.meetacy.backend.types.meetings.MeetingId
import app.meetacy.backend.types.users.UserId
import app.meetacy.di.builder.DIBuilder

internal fun DIBuilder.quitMeetingUsecase() {
    val quitMeetingUsecase by singleton {
        val authRepository: AuthRepository by getting
        val getMeetingsViewsRepository: GetMeetingsViewsRepository by getting

        val participantsStorage: ParticipantsStorage by getting

        val storage = object : QuitMeetingUsecase.Storage {
            override suspend fun quitMeeting(meetingId: MeetingId, userId: UserId) {
                participantsStorage.quitMeeting(meetingId, userId)
            }
        }

        QuitMeetingUsecase(
            authRepository = authRepository,
            getMeetingsViewsRepository = getMeetingsViewsRepository,
            storage = storage
        )
    }
}
