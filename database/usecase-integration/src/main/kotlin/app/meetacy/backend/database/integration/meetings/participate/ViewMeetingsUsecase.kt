package app.meetacy.backend.database.integration.meetings.participate

import app.meetacy.backend.database.meetings.ParticipantsTable
import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.meeting.IdMeeting
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.meetings.get.ViewMeetingsUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseViewMeetingsUsecaseStorage(db: Database) : ViewMeetingsUsecase.Storage {
    private val participantsTable = ParticipantsTable(db)

    override suspend fun getParticipantsCount(idMeetings: List<IdMeeting>): List<Int> =
        idMeetings.map { meetingId -> participantsTable.participantsCount(meetingId) }

    override suspend fun getIsParticipates(viewerId: UserId, idMeetings: List<IdMeeting>): List<Boolean> =
        idMeetings.map { meetingId -> participantsTable.isParticipating(meetingId, viewerId) }

    override suspend fun getFirstParticipants(
        limit: Amount,
        idMeetings: List<IdMeeting>
    ): List<List<UserId>> = idMeetings
        .map { meetingId ->
            participantsTable.getParticipants(meetingId, limit, pagingId = null)
        }.map { it.data }
}
