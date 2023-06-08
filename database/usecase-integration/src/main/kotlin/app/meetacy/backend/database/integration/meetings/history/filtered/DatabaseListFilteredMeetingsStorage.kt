package app.meetacy.backend.database.integration.meetings.history.filtered

import app.meetacy.backend.database.meetings.ParticipantsTable
import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.paging.PagingId
import app.meetacy.backend.types.paging.PagingResult
import app.meetacy.backend.types.user.UserId
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import org.jetbrains.exposed.sql.Database

class DatabaseListFilteredMeetingsStorage(db: Database) {
    private val participantsTable = ParticipantsTable(db)
    suspend fun getFilteredMeetings(
        memberId: UserId,
        amount: Amount,
        pagingId: PagingId?,
        filter: suspend (MeetingId) -> Boolean
    ): PagingResult<List<MeetingId>> {
        val data = participantsTable.getJoinHistoryFlow(memberId, pagingId)
            .map {
                it.data to it.nextPagingId
            }.filter { filter(it.first) }.take(amount.int).toList()
        val nextPagingId = data.last().second

        return PagingResult(data.map { (meetingId) -> meetingId }, nextPagingId)
    }
}
