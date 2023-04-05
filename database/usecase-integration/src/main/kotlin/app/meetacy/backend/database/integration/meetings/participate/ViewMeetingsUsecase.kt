package app.meetacy.backend.database.integration.meetings.participate

import app.meetacy.backend.database.meetings.ParticipantsTable
import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.meetings.get.ViewMeetingsUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseViewMeetingsUsecaseStorage(db: Database) : ViewMeetingsUsecase.Storage {
    private val participantsTable = ParticipantsTable(db)

    override suspend fun getParticipantsCount(meetingIds: List<MeetingId>): List<Int> =
        meetingIds.map { meetingId -> participantsTable.participantsCount(meetingId) }

    override suspend fun getIsParticipates(viewerId: UserId, meetingIds: List<MeetingId>): List<Boolean> =
        meetingIds.map { meetingId -> participantsTable.isParticipating(meetingId, viewerId) }

    override suspend fun getFirstParticipants(
        limit: Amount,
        meetingIds: List<MeetingId>
    ): List<List<UserId>> = meetingIds
        .map { meetingId ->
            participantsTable.getParticipants(meetingId, limit, pagingId = null)
        }.map { it.data }
}
