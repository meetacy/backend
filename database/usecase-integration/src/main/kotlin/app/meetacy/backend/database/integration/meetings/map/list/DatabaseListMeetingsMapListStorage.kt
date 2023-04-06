package app.meetacy.backend.database.integration.meetings.map.list

import app.meetacy.backend.database.integration.types.mapToUsecase
import app.meetacy.backend.database.meetings.MeetingsTable
import app.meetacy.backend.database.meetings.ParticipantsTable
import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.meetings.map.list.ListMeetingsMapUsecase
import app.meetacy.backend.usecase.types.FullMeeting
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.jetbrains.exposed.sql.Database

class DatabaseListMeetingsMapListStorage(db: Database) : ListMeetingsMapUsecase.Storage {
    private val participantsTable = ParticipantsTable(db)
    private val meetingsTable = MeetingsTable(db)

    override suspend fun getMeetingsHistoryFlow(userId: UserId): Flow<MeetingId> =
        participantsTable
            .getJoinHistoryFlow(userId, pagingId = null)
            .map { it.data }

    override suspend fun getPublicMeetingsFlow(): Flow<FullMeeting> =
        meetingsTable
            .getPublicMeetingsFlow()
            .map { it.mapToUsecase() }
}
