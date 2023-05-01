@file:Suppress("PrivatePropertyName")

package app.meetacy.backend.database.meetings

import app.meetacy.backend.types.amount.Amount
import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.paging.PagingId
import app.meetacy.backend.types.paging.PagingResult
import app.meetacy.backend.types.user.UserId
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

class ParticipantsTable(private val db: Database) : Table() {
    private val ID = long("ID").autoIncrement()
    private val MEETING_ID = long("MEETING_ID")
    private val USER_ID = long("USER_ID")

    override val primaryKey = PrimaryKey(ID)

    init {
        transaction(db) {
            SchemaUtils.create(this@ParticipantsTable)
        }
    }

    suspend fun addParticipant(participantId: UserId, meetingId: MeetingId) =
        newSuspendedTransaction(db = db) {
            insert { statement ->
                statement[MEETING_ID] = meetingId.long
                statement[USER_ID] = participantId.long
            }
        }

    suspend fun participantsCount(meetingId: MeetingId): Int =
        newSuspendedTransaction(db = db) {
            select { (MEETING_ID eq meetingId.long) }
                .count()
                .toInt()
        }

    suspend fun isParticipating(meetingId: MeetingId, userId: UserId): Boolean =
        newSuspendedTransaction(db = db) {
            select { (MEETING_ID eq meetingId.long) and (USER_ID eq userId.long) }.any()
        }

    suspend fun getJoinHistory(
        userId: UserId,
        amount: Amount,
        pagingId: PagingId?,
    ): PagingResult<List<MeetingId>> = newSuspendedTransaction(db = db) {
       val results = select {
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
    ): PagingResult<List<UserId>> = newSuspendedTransaction(db = db) {
        // todo ?
        val results = select { (MEETING_ID eq meetingId.long) and (ID less (pagingId?.long ?: Long.MAX_VALUE)) }
            .orderBy(ID, SortOrder.DESC)
            .take(amount.int)

        val nextPagingId = if (results.size == amount.int) PagingId(results.last()[ID]) else null

        PagingResult(
            data = results.map { UserId(it[USER_ID]) },
            nextPagingId = nextPagingId
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getJoinHistoryFlow(
        userId: UserId,
        pagingId: PagingId?,
    ): Flow<PagingResult<MeetingId>> = channelFlow {
        newSuspendedTransaction(db = db) {
            select { (USER_ID eq userId.long) and (ID less (pagingId?.long ?: Long.MAX_VALUE)) }
                .orderBy(ID, SortOrder.DESC)
                .asFlow()
                .map { row ->
                    PagingResult(
                        data = MeetingId(row[MEETING_ID]),
                        nextPagingId = PagingId(row[ID])
                    )
                }.collect(::send)
        }
    }
}
