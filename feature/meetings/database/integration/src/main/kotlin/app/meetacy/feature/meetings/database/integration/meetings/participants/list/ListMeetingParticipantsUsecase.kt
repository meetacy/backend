package app.meetacy.feature.meetings.database.integration.meetings.participants.list

import app.meetacy.backend.feature.meetings.database.meetings.ParticipantsStorage
import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.meetings.MeetingId
import app.meetacy.backend.types.paging.PagingId
import app.meetacy.backend.types.paging.PagingResult
import app.meetacy.backend.types.users.UserId
import app.meetacy.backend.feature.meetings.usecase.participants.list.ListMeetingParticipantsUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseListMeetingParticipantsStorage(db: Database) : ListMeetingParticipantsUsecase.Storage {
    private val participantsStorage = ParticipantsStorage(db)

    override suspend fun getMeetingParticipants(
        meetingId: MeetingId,
        amount: Amount,
        pagingId: PagingId?
    ): PagingResult<UserId> = participantsStorage.getParticipants(meetingId, amount, pagingId)
}
