package app.meetacy.backend.mock.integration.meetings.list

import app.meetacy.backend.mock.storage.MeetingsStorage
import app.meetacy.backend.types.MeetingId
import app.meetacy.backend.types.UserId
import app.meetacy.backend.mock.storage.ParticipantsStorage
import app.meetacy.backend.usecase.meetings.GetMeetingsListUsecase

object MockGetMeetingsListStorage : GetMeetingsListUsecase.Storage {

    override suspend fun getSelfMeetings(creatorId: UserId): List<MeetingId> =
        MeetingsStorage.getMeetingCreator(creatorId)


    override suspend fun getParticipatingMeetings(memberId: UserId): List<MeetingId> =
        ParticipantsStorage.getMeetingIds(memberId)
}
