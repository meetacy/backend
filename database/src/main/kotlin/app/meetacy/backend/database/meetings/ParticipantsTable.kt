@file:Suppress("PrivatePropertyName")

package app.meetacy.backend.database.meetings

import app.meetacy.backend.types.Amount
import app.meetacy.backend.types.MeetingId
import app.meetacy.backend.types.UserId
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

class ParticipantsTable(private val db: Database) : Table() {
    private val MEETING_ID = long("MEETING_ID")
    private val USER_ID = long("USER_ID")

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
            select { (MEETING_ID eq meetingId.long) and (USER_ID eq userId.long) }
                .any()
        }

    suspend fun getJoinHistory(
        userId: UserId,
        amount: Amount,
        lastMeetingId: MeetingId?
    ): List<MeetingId> =
        newSuspendedTransaction(db = db) {
            select { (USER_ID eq userId.long) and (MEETING_ID less (lastMeetingId?.long ?: Long.MAX_VALUE)) }
                .map { it[MEETING_ID] }
                .map(::MeetingId)
                .take(amount.int)
        }
}
