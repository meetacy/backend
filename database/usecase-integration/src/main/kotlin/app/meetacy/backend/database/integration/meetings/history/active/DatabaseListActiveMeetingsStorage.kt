package app.meetacy.backend.database.integration.meetings.history.active

import app.meetacy.backend.database.meetings.MeetingsTable
import app.meetacy.backend.database.meetings.ParticipantsTable
import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.datetime.Date
import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.paging.PagingId
import app.meetacy.backend.types.paging.PagingResult
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.meetings.history.active.ListMeetingsActiveUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseListActiveMeetingsStorage(db: Database): ListMeetingsActiveUsecase.Storage {
    private val participantsTable = ParticipantsTable(db)
    private val meetingsTable = MeetingsTable(db)
    override suspend fun getActiveMeetings(
        memberId: UserId,
        amount: Amount,
        pagingId: PagingId?
    ): PagingResult<List<MeetingId>> {
        val result = mutableListOf<MeetingId>()
        var currentPagingId = pagingId
        while (result.size < amount.int) {
            val paging = participantsTable.getJoinHistory(memberId, amount, currentPagingId)
            result.addAll(paging.data.filter { meetingsTable.getMeeting(it).date >= Date.today() })
            currentPagingId = paging.nextPagingId
            if (currentPagingId == null) break
        }
        return PagingResult(data = result.take(amount.int), nextPagingId = currentPagingId)
    }
}