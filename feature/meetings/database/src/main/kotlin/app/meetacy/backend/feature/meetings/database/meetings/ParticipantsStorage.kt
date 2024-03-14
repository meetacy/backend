@file:Suppress("PrivatePropertyName")

package app.meetacy.backend.feature.meetings.database.meetings

import app.meetacy.backend.database.exposed.query.wrapTransactionAsFlow
import app.meetacy.backend.feature.meetings.database.meetings.ParticipantsTable.ID
import app.meetacy.backend.feature.meetings.database.meetings.ParticipantsTable.MEETING_ID
import app.meetacy.backend.feature.meetings.database.meetings.ParticipantsTable.USER_ID
import app.meetacy.backend.feature.users.database.users.UsersTable
import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.meetings.MeetingId
import app.meetacy.backend.types.paging.PagingId
import app.meetacy.backend.types.paging.PagingResult
import app.meetacy.backend.types.paging.PagingValue
import app.meetacy.backend.types.paging.pagingIdLong
import app.meetacy.backend.types.users.UserId
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

object ParticipantsTable : Table() {
    val ID = long("ID").autoIncrement()
    val MEETING_ID = reference("MEETING_ID", MeetingsTable.MEETING_ID)
    val USER_ID = reference("USER_ID", UsersTable.USER_ID)

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

    suspend fun leaveMeeting(meetingId: MeetingId, userId: UserId) =
        newSuspendedTransaction(Dispatchers.IO, db) {
            ParticipantsTable.deleteWhere { ((MEETING_ID eq meetingId.long) and (USER_ID eq userId.long)) }
        }

    suspend fun participantsCount(meetingIds: List<MeetingId>): List<Int> =
        newSuspendedTransaction(Dispatchers.IO, db) {
            meetingIds.map { meetingId ->
                ParticipantsTable.select { MEETING_ID eq meetingId.long }
                    .count()
                    .toInt()
            }
        }

    suspend fun isParticipating(meetingIds: List<MeetingId>, userId: UserId): List<Boolean> =
        newSuspendedTransaction(Dispatchers.IO, db) {
            meetingIds.map { meetingId ->
                ParticipantsTable.select { (MEETING_ID eq meetingId.long) and (USER_ID eq userId.long) }.any()
            }
        }

    suspend fun getJoinHistory(
        userId: UserId,
        amount: Amount,
        pagingId: PagingId?,
    ): PagingResult<MeetingId> = newSuspendedTransaction(Dispatchers.IO, db) {
       val results = ParticipantsTable.select {
           (USER_ID eq userId.long) and (ID less (pagingId?.long ?: Long.MAX_VALUE))
       }.orderBy(ID, SortOrder.DESC).take(amount.int)

        PagingResult(
            data = results.map { result -> MeetingId(result[MEETING_ID]) },
            nextPagingId = results.pagingIdLong(amount) { it[ID] }
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

    suspend fun getFirstParticipants(
        meetingIds: List<MeetingId>,
        amount: Amount
    ): List<PagingResult<UserId>> = newSuspendedTransaction(Dispatchers.IO, db) {
        meetingIds.map { meetingId ->
            val results = ParticipantsTable.select { (MEETING_ID eq meetingId.long)}
                .orderBy(ID, SortOrder.DESC)
                .limit(amount.int)
                .toList()

            val nextPagingId = if (results.size == amount.int) PagingId(results.last()[ID]) else null

            PagingResult(
                data = results.map { UserId(it[USER_ID]) },
                nextPagingId = nextPagingId
            )
        }
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
