package app.meetacy.backend.mock.integration

import app.meetacy.backend.mock.storage.MeetingsStorage
import app.meetacy.backend.types.MeetingId
import app.meetacy.backend.types.UserId
import app.meetacy.backend.mock.storage.ParticipantsStorage
import app.meetacy.backend.usecase.meetings.GetMeetingsListUsecase

object MockGetMeetingsListStorage : GetMeetingsListUsecase.Storage {
    override fun getMeetingsList(memberId: UserId): List<MeetingId> {
        val meetingsCreator = MeetingsStorage.getMeetingCreator(memberId)
        val meetingsMember = ParticipantsStorage.getMeetingIds(memberId)
        return meetingsCreator + meetingsMember
    }
}
