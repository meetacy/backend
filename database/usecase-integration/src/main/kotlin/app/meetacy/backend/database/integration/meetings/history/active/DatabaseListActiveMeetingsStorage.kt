package app.meetacy.backend.database.integration.meetings.history.active

import app.meetacy.backend.database.integration.meetings.history.filtered.DatabaseListFilteredMeetingsStorage
import app.meetacy.backend.database.meetings.MeetingsTable
import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.datetime.Date
import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.paging.PagingId
import app.meetacy.backend.types.paging.PagingResult
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.meetings.history.active.ListMeetingsActiveUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseListActiveMeetingsStorage(
    private val listFilteredMeetingsStorage: DatabaseListFilteredMeetingsStorage,
    db: Database
) : ListMeetingsActiveUsecase.Storage {
    private val meetingsTable = MeetingsTable(db)
    private val filter: suspend (MeetingId) -> Boolean = { meetingsTable.getMeeting(it).date >= Date.today() }

    override suspend fun getActiveMeetings(
        memberId: UserId,
        amount: Amount,
        pagingId: PagingId?
    ): PagingResult<List<MeetingId>> = listFilteredMeetingsStorage
        .getFilteredMeetings(memberId, amount, pagingId, filter)
}
