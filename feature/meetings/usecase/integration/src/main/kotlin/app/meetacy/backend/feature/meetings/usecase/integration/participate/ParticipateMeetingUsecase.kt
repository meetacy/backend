package app.meetacy.backend.feature.meetings.usecase.integration.participate

import app.meetacy.backend.feature.meetings.database.meetings.ParticipantsStorage
import app.meetacy.backend.feature.meetings.usecase.participate.ParticipateMeetingUsecase
import app.meetacy.backend.types.auth.AuthRepository
import app.meetacy.backend.types.datetime.Date
import app.meetacy.backend.types.meetings.GetMeetingsViewsRepository
import app.meetacy.backend.types.meetings.MeetingId
import app.meetacy.backend.types.users.UserId
import app.meetacy.di.builder.DIBuilder

fun DIBuilder.participateMeetingUsecase() {
    val participateMeetingUsecase by singleton {
        val authRepository: AuthRepository by getting
        val getMeetingsViewsRepository: GetMeetingsViewsRepository by getting

        val participantsStorage: ParticipantsStorage by getting

        val storage = object : ParticipateMeetingUsecase.Storage {
            override suspend fun addParticipant(
                participantId: UserId, meetingId: MeetingId,
                meetingDate: Date
            ) {
                participantsStorage.addParticipant(participantId, meetingId, meetingDate)
            }
            override suspend fun isParticipating(meetingId: MeetingId, userId: UserId): Boolean {
                return participantsStorage.isParticipating(listOf(meetingId), userId).first()
            }
        }

        ParticipateMeetingUsecase(
            authRepository = authRepository,
            storage = storage,
            getMeetingsViewsRepository = getMeetingsViewsRepository
        )
    }
}
