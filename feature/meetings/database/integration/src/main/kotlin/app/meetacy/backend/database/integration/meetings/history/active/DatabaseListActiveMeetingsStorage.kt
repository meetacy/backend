package app.meetacy.backend.database.integration.meetings.history.active

import app.meetacy.backend.database.meetings.ParticipantsStorage
import app.meetacy.backend.feature.auth.usecase.meetings.history.active.ListMeetingsActiveUsecase
import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.paging.PagingId
import app.meetacy.backend.types.paging.PagingValue
import app.meetacy.backend.types.user.UserId
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
