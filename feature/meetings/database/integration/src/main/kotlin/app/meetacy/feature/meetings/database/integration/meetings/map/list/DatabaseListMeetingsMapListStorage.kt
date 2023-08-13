package app.meetacy.feature.meetings.database.integration.meetings.map.list

import app.meetacy.feature.meetings.database.integration.types.mapToUsecase
import app.meetacy.backend.feature.meetings.database.meetings.MeetingsStorage
import app.meetacy.backend.feature.meetings.database.meetings.ParticipantsStorage
import app.meetacy.backend.types.meetings.FullMeeting
import app.meetacy.backend.types.meetings.MeetingId
import app.meetacy.backend.types.users.UserId
import app.meetacy.backend.feature.meetings.usecase.map.list.ListMeetingsMapUsecase
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
