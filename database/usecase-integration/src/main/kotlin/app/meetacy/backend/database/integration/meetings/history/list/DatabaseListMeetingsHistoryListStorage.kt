package app.meetacy.backend.database.integration.meetings.history.list

import app.meetacy.backend.database.meetings.ParticipantsStorage
import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.paging.PagingId
import app.meetacy.backend.types.paging.PagingResult
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.meetings.history.list.ListMeetingsHistoryUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseListMeetingsHistoryListStorage(db: Database) : ListMeetingsHistoryUsecase.Storage {
    private val participantsStorage = ParticipantsStorage(db)

    override suspend fun getParticipatingMeetings(
        memberId: UserId,
        amount: Amount,
        pagingId: PagingId?
    ): PagingResult<List<MeetingId>> = participantsStorage
        .getJoinHistory(memberId, amount, pagingId)
}
