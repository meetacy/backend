package app.meetacy.backend.mock.integration

import app.meetacy.backend.domain.MeetingId
import app.meetacy.backend.domain.UserId
import app.meetacy.backend.mock.storage.ParticipantsStorage
import app.meetacy.backend.usecase.meetings.ViewMeetingsUsecase

private object MockViewMeetingsUsecaseStorage : ViewMeetingsUsecase.Storage {
    override suspend fun getParticipantsCount(meetingIds: List<MeetingId>): List<Int> =
        meetingIds.map { meetingId -> ParticipantsStorage.participantsCount(meetingId) }

    override suspend fun getParticipations(viewerId: UserId, meetingIds: List<MeetingId>): List<Boolean> =
        meetingIds.map { meetingId -> ParticipantsStorage.isParticipating(meetingId, viewerId) }
}

fun mockViewMeetingsUsecase(): ViewMeetingsUsecase = ViewMeetingsUsecase(
    getUsersViewsRepository = MockGetUsersViewsRepository,
    storage = MockViewMeetingsUsecaseStorage
)
