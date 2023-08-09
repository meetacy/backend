package app.meetacy.backend.database.integration.meetings.map.list

import app.meetacy.backend.feature.auth.database.integration.types.mapToUsecase
import app.meetacy.backend.database.meetings.MeetingsStorage
import app.meetacy.backend.database.meetings.ParticipantsStorage
import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.meetings.map.list.ListMeetingsMapUsecase
import app.meetacy.backend.usecase.types.FullMeeting
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.jetbrains.exposed.sql.Database

class DatabaseListMeetingsMapListStorage(db: Database) : ListMeetingsMapUsecase.Storage {
    private val participantsStorage = ParticipantsStorage(db)
    private val meetingsStorage = MeetingsStorage(db)

    override suspend fun getMeetingsHistoryFlow(userId: UserId): Flow<MeetingId> =
        participantsStorage
            .getJoinHistoryFlow(userId, pagingId = null)
            .map { it.value }

    override suspend fun getPublicMeetingsFlow(): Flow<FullMeeting> =
        meetingsStorage
            .getPublicMeetingsFlow()
            .map { it.mapToUsecase() }
}
