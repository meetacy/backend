@file:Suppress("PrivatePropertyName")

package app.meetacy.backend.database.meetings

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

    suspend fun addParticipant(meetingId: MeetingId, userId: UserId) =
        newSuspendedTransaction(db = db) {
            insert { statement ->
                statement[MEETING_ID] = meetingId.long
                statement[USER_ID] = userId.long
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

    suspend fun getMeetingIds(userId: UserId): List<MeetingId> =
        newSuspendedTransaction(db = db) {
             select { (USER_ID eq userId.long) }
                 .map { it[MEETING_ID] }
                 .map { MeetingId(it) }
        }
}
