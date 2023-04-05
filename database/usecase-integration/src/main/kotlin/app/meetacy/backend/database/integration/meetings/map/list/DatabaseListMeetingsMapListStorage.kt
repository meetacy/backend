package app.meetacy.backend.database.integration.meetings.map.list

import app.meetacy.backend.database.meetings.ParticipantsTable
import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.meetings.map.list.ListMeetingsMapUsecase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.jetbrains.exposed.sql.Database

class DatabaseListMeetingsMapListStorage(db: Database) : ListMeetingsMapUsecase.Storage {
    private val participantsTable = ParticipantsTable(db)

    override suspend fun getMeetingsHistoryFlow(userId: UserId): Flow<MeetingId> =
        participantsTable.getJoinHistoryFlow(userId, pagingId = null).map { it.data }
}
