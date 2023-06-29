@file:Suppress("PrivatePropertyName")

package app.meetacy.backend.database.meetings

import app.meetacy.backend.database.files.FilesTable
import app.meetacy.backend.database.meetings.MeetingsTable.ACCESS_HASH
import app.meetacy.backend.database.meetings.MeetingsTable.AVATAR_ID
import app.meetacy.backend.database.meetings.MeetingsTable.CREATOR_ID
import app.meetacy.backend.database.meetings.MeetingsTable.DATE
import app.meetacy.backend.database.meetings.MeetingsTable.DESCRIPTION
import app.meetacy.backend.database.meetings.MeetingsTable.LATITUDE
import app.meetacy.backend.database.meetings.MeetingsTable.LONGITUDE
import app.meetacy.backend.database.meetings.MeetingsTable.MEETING_ID
import app.meetacy.backend.database.meetings.MeetingsTable.TITLE
import app.meetacy.backend.database.meetings.MeetingsTable.VISIBILITY
import app.meetacy.backend.database.types.DatabaseMeeting
import app.meetacy.backend.database.users.UsersTable
import app.meetacy.backend.types.*
import app.meetacy.backend.types.access.AccessHash
import app.meetacy.backend.types.annotation.UnsafeConstructor
import app.meetacy.backend.types.datetime.Date
import app.meetacy.backend.types.file.FileId
import app.meetacy.backend.types.location.Location
import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.meeting.MeetingIdentity
import app.meetacy.backend.types.user.UserId
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.map
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

object MeetingsTable : Table() {
    val MEETING_ID = long("MEETING_ID").autoIncrement()
    val ACCESS_HASH = varchar("ACCESS_HASH", length = HASH_LENGTH)
    val CREATOR_ID = reference("CREATOR_ID", UsersTable.USER_ID)
    val DATE = varchar("DATE", length = DATE_TIME_MAX_LIMIT)
    val LATITUDE = double("LATITUDE")
    val LONGITUDE = double("LONGITUDE")
    val TITLE = varchar("TITLE", length = MEETING_TITLE_MAX_LIMIT).nullable()
    val DESCRIPTION = varchar("DESCRIPTION", length = DESCRIPTION_MAX_LIMIT).nullable()
    val AVATAR_ID = reference("AVATAR_ID", FilesTable.FILE_ID).nullable()
    val VISIBILITY = enumeration("VISIBILITY", klass = DatabaseMeeting.Visibility::class)

    override val primaryKey = PrimaryKey(MEETING_ID)
}

class MeetingsStorage(private val db: Database) {
    suspend fun addMeeting(
        accessHash: AccessHash,
        creatorId: UserId,
        date: Date,
        location: Location,
        title: String?,
        description: String?,
        visibility: DatabaseMeeting.Visibility,
        avatarId: FileId?
    ): MeetingId =
        newSuspendedTransaction(Dispatchers.IO, db) {
            val meetingId =MeetingsTable.insert { statement ->
                statement[ACCESS_HASH] = accessHash.string
                statement[CREATOR_ID] = creatorId.long
                statement[DATE] = date.iso8601
                statement[LATITUDE] = location.latitude
                statement[LONGITUDE] = location.longitude
                statement[TITLE] = title
                statement[DESCRIPTION] = description
                statement[VISIBILITY] = visibility
                if (avatarId != null) statement[AVATAR_ID] = avatarId.long
            }[MEETING_ID]
            return@newSuspendedTransaction MeetingId(meetingId)
        }

    suspend fun getMeeting(id: MeetingId): DatabaseMeeting =
        getMeetingOrNull(id) ?: error("Cannot find a meeting with id $id")

    suspend fun getMeetingOrNull(id: MeetingId): DatabaseMeeting? =
        getMeetingsOrNull(listOf(id)).first()

    suspend fun getMeetingsOrNull(meetingIds: List<MeetingId>): List<DatabaseMeeting?> =
        newSuspendedTransaction(Dispatchers.IO, db) {
            val rawMeetingIds = meetingIds.map { it.long }

            val foundMeetings = MeetingsTable.select { MEETING_ID inList rawMeetingIds }
                .map { it.toDatabaseMeeting() }
                .associateBy { it.id }

            return@newSuspendedTransaction meetingIds.map { foundMeetings[it] }
        }

    suspend fun getCreatorMeetings(creatorId: UserId): List<MeetingId> =
        newSuspendedTransaction(Dispatchers.IO, db) {
            val result = MeetingsTable.select { (CREATOR_ID eq creatorId.long) }
                .map { statement -> statement.toDatabaseMeeting() }
            return@newSuspendedTransaction result.filter { it.creatorId == creatorId }.map { it.id }
        }

    suspend fun deleteMeeting(meetingId: MeetingId) =
        newSuspendedTransaction(Dispatchers.IO, db) {
            MeetingsTable.deleteWhere { ((MEETING_ID eq meetingId.long)) }
        }

    suspend fun editMeeting(
        meetingId: MeetingId,
        avatarId: Optional<FileId?>,
        title: String?,
        description: String?,
        location: Location?,
        date: Date?,
        visibility: DatabaseMeeting.Visibility?
    ): DatabaseMeeting = newSuspendedTransaction(Dispatchers.IO, db) {
        MeetingsTable.update({ MEETING_ID eq meetingId.long }) { statement ->
            title?.let { statement[TITLE] = it }
            description?.let { statement[DESCRIPTION] = it }
            location?.let {
                statement[LATITUDE] = it.latitude
                statement[LONGITUDE] = it.longitude
            }
            date?.let { statement[DATE]= it.iso8601 }
            visibility?.let { statement[VISIBILITY] = it }
            avatarId.ifPresent {
                statement[AVATAR_ID] = it?.long
            }
        }

        return@newSuspendedTransaction MeetingsTable.select { MEETING_ID eq meetingId.long }
            .first()
            .toDatabaseMeeting()
    }

    fun getPublicMeetingsFlow(): Flow<DatabaseMeeting> = channelFlow {
        newSuspendedTransaction(Dispatchers.IO, db) {
            MeetingsTable.select { VISIBILITY eq DatabaseMeeting.Visibility.Public }
                .asFlow()
                .map { it.toDatabaseMeeting() }
                .collect(::send)
        }
    }

    @OptIn(UnsafeConstructor::class)
    private fun ResultRow.toDatabaseMeeting(): DatabaseMeeting =
        DatabaseMeeting(
            identity = MeetingIdentity(
                meetingId = MeetingId(this[MEETING_ID]),
                accessHash = AccessHash(this[ACCESS_HASH])
            ),
            creatorId = UserId(this[CREATOR_ID]),
            date = Date(this[DATE]),
            location = Location(this[LATITUDE], this[LONGITUDE]),
            description = this[DESCRIPTION],
            title = this[TITLE],
            avatarId = this[AVATAR_ID]?.let(::FileId),
            visibility = this[VISIBILITY]
        )
}
