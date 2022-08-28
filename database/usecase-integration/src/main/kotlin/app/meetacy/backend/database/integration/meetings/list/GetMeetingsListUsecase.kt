package app.meetacy.backend.database.integration.meetings.list

import app.meetacy.backend.database.meetings.MeetingsTable
import app.meetacy.backend.database.meetings.ParticipantsTable
import app.meetacy.backend.types.MeetingId
import app.meetacy.backend.types.UserId
import app.meetacy.backend.usecase.meetings.GetMeetingsListUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseGetMeetingsListStorage(private val db: Database) : GetMeetingsListUsecase.Storage {

    override suspend fun getSelfMeetings(creatorId: UserId): List<MeetingId> =
        MeetingsTable(db).getMeetingCreator(creatorId)


    override suspend fun getParticipatingMeetings(memberId: UserId): List<MeetingId> =
        ParticipantsTable(db).getMeetingIds(memberId)
}