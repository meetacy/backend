@file:Suppress("PrivatePropertyName")

package app.meetacy.backend.database.meetings

import app.meetacy.backend.types.MeetingId
import app.meetacy.backend.types.UserId
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class ParticipantsTable(private val db: Database) : Table() {
    private val MEETING_ID = long("MEETING_ID")
    private val USER_ID = long("USER_ID")

    init {
        transaction(db) {
            SchemaUtils.create(this@ParticipantsTable)
        }
    }

    fun addParticipant(meetingId: MeetingId, userId: UserId) =
        transaction(db) {
            insert { statement ->
                statement[MEETING_ID] = meetingId.long
                statement[USER_ID] = userId.long
            }
        }

    fun participantsCount(meetingId: MeetingId): Int =
        transaction(db) {
            select { (MEETING_ID eq meetingId.long) }
                .count()
                .toInt()
        }


    fun isParticipating(meetingId: MeetingId, userId: UserId): Boolean =
        transaction(db) {
            select { (MEETING_ID eq meetingId.long) and (USER_ID eq userId.long) }
                .any()
        }

    fun getMeetingIds(userId: UserId): List<MeetingId> =
        transaction(db) {
             select { (USER_ID eq userId.long) }
                 .map { it[MEETING_ID] }
                 .map { MeetingId(it) }
        }
}
