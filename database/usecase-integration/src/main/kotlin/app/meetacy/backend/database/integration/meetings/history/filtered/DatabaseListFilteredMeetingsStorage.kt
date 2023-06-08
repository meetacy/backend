package app.meetacy.backend.database.integration.meetings.history.filtered

import app.meetacy.backend.database.meetings.ParticipantsTable
import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.paging.PagingId
import app.meetacy.backend.types.paging.PagingResult
import app.meetacy.backend.types.user.UserId
import org.jetbrains.exposed.sql.Database

class DatabaseListFilteredMeetingsStorage(db: Database) {
    private val participantsTable = ParticipantsTable(db)
    suspend fun getFilteredMeetings(
        memberId: UserId,
        amount: Amount,
        pagingId: PagingId?,
        filter: suspend (MeetingId) -> Boolean
    ): PagingResult<List<MeetingId>> {
        val result = mutableListOf<MeetingId>()
        var currentPagingId = pagingId
        while (result.size < amount.int) {
            val paging = participantsTable.getJoinHistory(memberId, amount, currentPagingId)
            result.addAll(paging.data.filter { filter(it) })
            currentPagingId = paging.nextPagingId
            if (currentPagingId == null) break
        }
        return PagingResult(data = result.take(amount.int), nextPagingId = currentPagingId)
    }
}
