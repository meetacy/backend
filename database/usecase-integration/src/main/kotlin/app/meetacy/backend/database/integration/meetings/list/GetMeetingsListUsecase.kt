package app.meetacy.backend.database.integration.meetings.list

import app.meetacy.backend.database.meetings.ParticipantsTable
import app.meetacy.backend.types.Amount
import app.meetacy.backend.types.MeetingId
import app.meetacy.backend.types.UserId
import app.meetacy.backend.usecase.meetings.history.list.ListMeetingsHistoryUsecase
import org.jetbrains.exposed.sql.Database

class DatabaseGetMeetingsListStorage(private val db: Database) : ListMeetingsHistoryUsecase.Storage {
    private val participantsTable = ParticipantsTable(db)

    override suspend fun getParticipatingMeetings(
        memberId: UserId,
        amount: Amount,
        lastMeetingId: MeetingId?
    ): List<MeetingId> = participantsTable.getJoinHistory(
        userId = memberId,
        amount, lastMeetingId
    )
}
