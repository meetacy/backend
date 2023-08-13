package app.meetacy.feature.meetings.database.integration.meetings.history.active

import app.meetacy.backend.feature.meetings.database.meetings.ParticipantsStorage
import app.meetacy.backend.types.meetings.MeetingId
import app.meetacy.backend.types.paging.PagingId
import app.meetacy.backend.types.paging.PagingValue
import app.meetacy.backend.types.users.UserId
import app.meetacy.backend.feature.meetings.usecase.history.active.ListMeetingsActiveUsecase
import kotlinx.coroutines.flow.Flow
import org.jetbrains.exposed.sql.Database

class DatabaseListActiveMeetingsStorage(
    db: Database
) : ListMeetingsActiveUsecase.Storage {
    private val participantsStorage = ParticipantsStorage(db)

    override suspend fun getJoinHistoryFlow(
        userId: UserId,
        startPagingId: PagingId?
    ): Flow<PagingValue<MeetingId>> {
        return participantsStorage.getJoinHistoryFlow(userId, startPagingId)
    }
}
