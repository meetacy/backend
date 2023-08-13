package app.meetacy.feature.meetings.database.integration.meetings.history.past

import app.meetacy.backend.feature.meetings.database.meetings.ParticipantsStorage
import app.meetacy.backend.types.meetings.MeetingId
import app.meetacy.backend.types.paging.PagingId
import app.meetacy.backend.types.paging.PagingValue
import app.meetacy.backend.types.users.UserId
import app.meetacy.backend.feature.meetings.usecase.history.past.ListMeetingsPastUsecase
import kotlinx.coroutines.flow.Flow
import org.jetbrains.exposed.sql.Database

class DatabaseListPastMeetingsStorage(
    db: Database
) : ListMeetingsPastUsecase.Storage {
    private val participantsTable = ParticipantsStorage(db)

    override suspend fun getJoinHistoryFlow(
        userId: UserId,
        startPagingId: PagingId?
    ): Flow<PagingValue<MeetingId>> = participantsTable.getJoinHistoryFlow(userId, startPagingId)
}
