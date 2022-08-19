package app.meetacy.backend.mock.integration.meetings.participate

import app.meetacy.backend.types.MeetingId
import app.meetacy.backend.types.UserId
import app.meetacy.backend.mock.storage.ParticipantsStorage
import app.meetacy.backend.usecase.meetings.ViewMeetingsUsecase

object MockViewMeetingsUsecaseStorage : ViewMeetingsUsecase.Storage {
    override suspend fun getParticipantsCount(meetingIds: List<MeetingId>): List<Int> =
        meetingIds.map { meetingId -> ParticipantsStorage.participantsCount(meetingId) }

    override suspend fun getParticipations(viewerId: UserId, meetingIds: List<MeetingId>): List<Boolean> =
        meetingIds.map { meetingId -> ParticipantsStorage.isParticipating(meetingId, viewerId) }
}
