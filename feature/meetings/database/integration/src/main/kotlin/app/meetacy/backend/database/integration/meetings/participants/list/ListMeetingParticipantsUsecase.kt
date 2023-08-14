package app.meetacy.backend.database.integration.meetings.participants.list

import app.meetacy.backend.database.meetings.ParticipantsStorage
import app.meetacy.backend.feature.auth.usecase.meetings.participants.list.ListMeetingParticipantsUsecase
import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.paging.PagingId
import app.meetacy.backend.types.paging.PagingResult
import app.meetacy.backend.types.user.UserId
import org.jetbrains.exposed.sql.Database

class DatabaseListMeetingParticipantsStorage(db: Database) : ListMeetingParticipantsUsecase.Storage {
    private val participantsStorage = ParticipantsStorage(db)

    override suspend fun getMeetingParticipants(
        meetingId: MeetingId,
        amount: Amount,
        pagingId: PagingId?
    ): PagingResult<UserId> = participantsStorage.getParticipants(meetingId, amount, pagingId)
}
