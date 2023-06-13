@file:Suppress("PrivatePropertyName")

package app.meetacy.backend.database.meetings

import app.meetacy.backend.database.meetings.ParticipantsTable.ID
import app.meetacy.backend.database.meetings.ParticipantsTable.MEETING_ID
import app.meetacy.backend.database.meetings.ParticipantsTable.USER_ID
import app.meetacy.backend.database.transaction.wrapTransactionAsFlow
import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.paging.PagingId
import app.meetacy.backend.types.paging.PagingValue
import app.meetacy.backend.types.paging.PagingResult
import app.meetacy.backend.types.user.UserId
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

object ParticipantsTable : Table() {
    val ID = long("ID").autoIncrement()
    val MEETING_ID = long("MEETING_ID")
    val USER_ID = long("USER_ID")

    override val primaryKey = PrimaryKey(ID)
}

class ParticipantsStorage(private val db: Database) {

    suspend fun addParticipant(participantId: UserId, meetingId: MeetingId) =
        newSuspendedTransaction(Dispatchers.IO, db) {
            ParticipantsTable.insert { statement ->
                statement[MEETING_ID] = meetingId.long
                statement[USER_ID] = participantId.long
            }
        }

    suspend fun participantsCount(meetingId: MeetingId): Int =
        newSuspendedTransaction(Dispatchers.IO, db) {
            ParticipantsTable.select { (MEETING_ID eq meetingId.long) }
                .count()
                .toInt()
        }

    suspend fun isParticipating(meetingId: MeetingId, userId: UserId): Boolean =
        newSuspendedTransaction(Dispatchers.IO, db) {
            ParticipantsTable.select { (MEETING_ID eq meetingId.long) and (USER_ID eq userId.long) }.any()
        }

    suspend fun getJoinHistory(
        userId: UserId,
        amount: Amount,
        pagingId: PagingId?,
    ): PagingResult<MeetingId> = newSuspendedTransaction(Dispatchers.IO, db) {
       val results = ParticipantsTable.select {
           (USER_ID eq userId.long) and (ID less (pagingId?.long ?: Long.MAX_VALUE))
       }.orderBy(ID, SortOrder.DESC).take(amount.int)

        val nextPagingId = if (results.size == amount.int) PagingId(results.last()[ID]) else null

        PagingResult(
            data = results.map { result -> MeetingId(result[MEETING_ID]) },
            nextPagingId = nextPagingId
        )
    }

    suspend fun getParticipants(
        meetingId: MeetingId,
        amount: Amount,
        pagingId: PagingId?
    ): PagingResult<UserId> = newSuspendedTransaction(Dispatchers.IO, db) {
        val results = ParticipantsTable.select { (MEETING_ID eq meetingId.long) and (ID less (pagingId?.long ?: Long.MAX_VALUE)) }
            .orderBy(ID, SortOrder.DESC)
            .take(amount.int)

        val nextPagingId = if (results.size == amount.int) PagingId(results.last()[ID]) else null

        PagingResult(
            data = results.map { UserId(it[USER_ID]) },
            nextPagingId = nextPagingId
        )
    }

    fun getJoinHistoryFlow(
        userId: UserId,
        pagingId: PagingId?,
    ): Flow<PagingValue<MeetingId>> = ParticipantsTable
        .select { (USER_ID eq userId.long) and (ID less (pagingId?.long ?: Long.MAX_VALUE)) }
        .orderBy(ID, SortOrder.DESC)
        .wrapTransactionAsFlow(db)
        .map { row ->
            PagingValue(
                value = MeetingId(row[MEETING_ID]),
                nextPagingId = PagingId(row[ID])
            )
        }
}
