@file:Suppress("PrivatePropertyName")

package app.meetacy.backend.database.meetings.invites

import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.meeting.inviteCode.MeetingInviteCode
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

class MeetingInviteCodesTable(private val db: Database) : Table() {
    private val MEETING_INVITE_ID = long("MEETING_INVITE_ID").autoIncrement()
    private val MEETING_ID = long("MEETING_ID")
    private val INVITE_CODE = varchar("INVITE_CODE", 512) // todo max-512

    init {
        transaction(db) {
            SchemaUtils.create(this@MeetingInviteCodesTable)
        }
    }


    suspend fun isInviteCodeFree(inviteCode: MeetingInviteCode): Boolean = newSuspendedTransaction(db = db) {
        val result = select { INVITE_CODE eq inviteCode.string}.firstOrNull()
        return@newSuspendedTransaction result == null
    }

    suspend fun create(meetingId: MeetingId, inviteCode: MeetingInviteCode) = newSuspendedTransaction(db = db) {
        insert { statement ->
            statement[MEETING_ID] = meetingId.long
            statement[INVITE_CODE] = inviteCode.string
        }
    }

    suspend fun getMeetingInviteCodes(meetingId: MeetingId): List<MeetingInviteCode> = newSuspendedTransaction(db = db) {
        val result = select { MEETING_ID eq meetingId.long }.map {
            MeetingInviteCode(it[INVITE_CODE])
        }
        return@newSuspendedTransaction result
    }

    suspend fun getMeetingId(inviteCode: MeetingInviteCode): MeetingId? = newSuspendedTransaction(db = db) {
        val statement = select { INVITE_CODE eq inviteCode.string }
            .firstOrNull() ?: return@newSuspendedTransaction null
        return@newSuspendedTransaction MeetingId(statement[MEETING_ID])
    }

    suspend fun removeAll(meetingId: MeetingId) = newSuspendedTransaction(db = db) {
        deleteWhere { MEETING_ID eq meetingId.long }
    }

    suspend fun remove(inviteCode: MeetingInviteCode) = newSuspendedTransaction(db = db) {
        deleteWhere { INVITE_CODE eq inviteCode.string }
    }

}