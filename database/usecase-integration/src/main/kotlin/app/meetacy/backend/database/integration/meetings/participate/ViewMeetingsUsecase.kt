package app.meetacy.backend.database.integration.meetings.participate

import app.meetacy.backend.database.meetings.ParticipantsTable
import app.meetacy.backend.types.MeetingId
import app.meetacy.backend.types.UserId
import app.meetacy.backend.usecase.meetings.ViewMeetingsUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseViewMeetingsUsecaseStorage(db: Database) : ViewMeetingsUsecase.Storage {
    private val participantsTable = ParticipantsTable(db)

    override suspend fun getParticipantsCount(meetingIds: List<MeetingId>): List<Int> =
        meetingIds.map { meetingId -> participantsTable.participantsCount(meetingId) }

    override suspend fun getParticipations(viewerId: UserId, meetingIds: List<MeetingId>): List<Boolean> =
        meetingIds.map { meetingId -> participantsTable.isParticipating(meetingId, viewerId) }
}