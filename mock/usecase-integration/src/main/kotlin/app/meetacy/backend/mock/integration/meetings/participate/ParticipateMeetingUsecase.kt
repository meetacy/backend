package app.meetacy.backend.mock.integration.meetings.participate

import app.meetacy.backend.mock.storage.ParticipantsStorage
import app.meetacy.backend.types.MeetingId
import app.meetacy.backend.types.UserId
import app.meetacy.backend.usecase.meetings.ParticipateMeetingUsecase

object MockParticipateMeetingStorage : ParticipateMeetingUsecase.Storage {
    override suspend fun addParticipant(meetingId: MeetingId, userId: UserId) {
        ParticipantsStorage
            .addParticipant(meetingId, userId)
    }
    override suspend fun isParticipating(meetingId: MeetingId, userId: UserId): Boolean =
        ParticipantsStorage
            .isParticipating(meetingId, userId)
}
