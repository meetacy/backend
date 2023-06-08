package app.meetacy.backend.database.integration.meetings.history.past

import app.meetacy.backend.database.integration.meetings.history.filtered.DatabaseListFilteredMeetingsStorage
import app.meetacy.backend.database.meetings.MeetingsTable
import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.datetime.Date
import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.paging.PagingId
import app.meetacy.backend.types.paging.PagingResult
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.meetings.history.past.ListMeetingsPastUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseListPastMeetingsStorage(
    private val listFilteredMeetingsStorage: DatabaseListFilteredMeetingsStorage,
    db: Database
) : ListMeetingsPastUsecase.Storage {
    private val meetingsTable = MeetingsTable(db)
    private val filter: suspend (MeetingId) -> Boolean = { meetingsTable.getMeeting(it).date < Date.today() }

    override suspend fun getPastMeetings(
        memberId: UserId,
        amount: Amount,
        pagingId: PagingId?
    ): PagingResult<List<MeetingId>> = listFilteredMeetingsStorage
        .getFilteredMeetings(memberId, amount, pagingId, filter)
}
