package app.meetacy.backend.database.integration.meetings.participate

import app.meetacy.backend.database.meetings.ParticipantsStorage
import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.meetings.MeetingId
import app.meetacy.backend.types.users.UserId
import app.meetacy.backend.usecase.meetings.get.ViewMeetingsUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseViewMeetingsUsecaseStorage(db: Database) : ViewMeetingsUsecase.Storage {
    private val participantsStorage = ParticipantsStorage(db)

    override suspend fun getParticipantsCount(meetingIds: List<MeetingId>): List<Int> =
        meetingIds.map { meetingId -> participantsStorage.participantsCount(meetingId) }

    override suspend fun getIsParticipates(viewerId: UserId, meetingIds: List<MeetingId>): List<Boolean> =
        meetingIds.map { meetingId -> participantsStorage.isParticipating(meetingId, viewerId) }

    override suspend fun getFirstParticipants(
        limit: Amount,
        meetingIds: List<MeetingId>
    ): List<List<UserId>> = meetingIds
        .map { meetingId ->
            participantsStorage.getParticipants(meetingId, limit, pagingId = null)
        }.map { it.data }
}
