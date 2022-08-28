package app.meetacy.backend.database.integration.meetings.participate

import app.meetacy.backend.database.meetings.ParticipantsTable
import app.meetacy.backend.types.MeetingId
import app.meetacy.backend.types.UserId
import app.meetacy.backend.usecase.meetings.ViewMeetingsUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseViewMeetingsUsecaseStorage(private val db: Database) : ViewMeetingsUsecase.Storage {
    override suspend fun getParticipantsCount(meetingIds: List<MeetingId>): List<Int> =
        meetingIds.map { meetingId -> ParticipantsTable(db).participantsCount(meetingId) }

    override suspend fun getParticipations(viewerId: UserId, meetingIds: List<MeetingId>): List<Boolean> =
        meetingIds.map { meetingId -> ParticipantsTable(db).isParticipating(meetingId, viewerId) }
}