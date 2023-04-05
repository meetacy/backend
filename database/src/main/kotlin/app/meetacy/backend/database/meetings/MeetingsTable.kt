@file:Suppress("PrivatePropertyName")

package app.meetacy.backend.database.meetings

import app.meetacy.backend.database.types.DatabaseMeeting
import app.meetacy.backend.types.*
import app.meetacy.backend.types.access.AccessHash
import app.meetacy.backend.types.datetime.Date
import app.meetacy.backend.types.datetime.DateOrTime
import app.meetacy.backend.types.file.FileId
import app.meetacy.backend.types.file.FileIdentity
import app.meetacy.backend.types.location.Location
import app.meetacy.backend.types.meeting.MeetingId
import app.meetacy.backend.types.meeting.MeetingIdentity
import app.meetacy.backend.types.user.UserId
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

class MeetingsTable(private val db: Database) : Table() {
    private val MEETING_ID = long("MEETING_ID").autoIncrement()
    private val ACCESS_HASH = varchar("ACCESS_HASH", length = HASH_LENGTH)
    private val CREATOR_ID = long("CREATOR_ID")
    private val DATE = varchar("DATE", length = DATA_MAX_LIMIT)
    private val LATITUDE = double("LATITUDE")
    private val LONGITUDE = double("LONGITUDE")
    private val TITLE = varchar("TITLE", length = TITLE_MAX_LIMIT).nullable()
    private val DESCRIPTION = varchar("DESCRIPTION", length = DESCRIPTION_MAX_LIMIT).nullable()
    private val AVATAR_ID = long("AVATAR_ID").nullable()
    private val AVATAR_HASH = varchar("AVATAR_HASH", length = HASH_LENGTH).nullable()
    private val VISIBILITY = enumeration("VISIBILITY", klass = DatabaseMeeting.Visibility::class)

    override val primaryKey = PrimaryKey(MEETING_ID)

    init {
        transaction(db) {
            SchemaUtils.create(this@MeetingsTable)
        }
    }

    suspend fun addMeeting(
        accessHash: AccessHash,
        creatorId: UserId,
        date: DateOrTime,
        location: Location,
        title: String?,
        description: String?,
        visibility: DatabaseMeeting.Visibility
    ): MeetingId =
        newSuspendedTransaction(db = db) {
            val meetingId = insert { statement ->
                statement[ACCESS_HASH] = accessHash.string
                statement[CREATOR_ID] = creatorId.long
                statement[DATE] = date.iso8601
                statement[LATITUDE] = location.latitude
                statement[LONGITUDE] = location.longitude
                statement[TITLE] = title
                statement[DESCRIPTION] = description
                statement[VISIBILITY] = visibility
            }[MEETING_ID]
            return@newSuspendedTransaction MeetingId(meetingId)
        }

    suspend fun getMeeting(id: MeetingId): DatabaseMeeting =
        getMeetingOrNull(id) ?: error("Cannot find a meeting with id $id")

    suspend fun getMeetingOrNull(id: MeetingId): DatabaseMeeting? =
        getMeetingsOrNull(listOf(id)).first()

    suspend fun getMeetingsOrNull(meetingIds: List<MeetingId>): List<DatabaseMeeting?> =
        newSuspendedTransaction(db = db) {
            val rawMeetingIds = meetingIds.map { it.long }

            val foundMeetings = select { MEETING_ID inList rawMeetingIds }
                .map { it.toDatabaseMeeting() }
                .associateBy { it.id }

            return@newSuspendedTransaction meetingIds.map { foundMeetings[it] }
        }

    suspend fun getMeetingCreator(creatorId: UserId): List<MeetingId> =
        newSuspendedTransaction(db = db) {
            val result = select { (CREATOR_ID eq creatorId.long) }
                .map { statement -> statement.toDatabaseMeeting() }
            return@newSuspendedTransaction result.filter { it.creatorId == creatorId }.map { it.id }
        }

    suspend fun addAvatar(meetingId: MeetingId, avatarIdentity: FileIdentity) =
        newSuspendedTransaction(db = db) {
            update({ MEETING_ID eq meetingId.long }) {statement ->
                statement[AVATAR_ID] = avatarIdentity.fileId.long
                statement[AVATAR_HASH] = avatarIdentity.accessHash.string
            }
        }

    suspend fun deleteAvatar(meetingId: MeetingId) =
        newSuspendedTransaction(db = db) {
            update({ MEETING_ID eq meetingId.long }) {statement ->
                statement[AVATAR_ID] = null
                statement[AVATAR_HASH] = null
            }
        }

    suspend fun deleteMeeting(meetingId: MeetingId) =
        newSuspendedTransaction(db = db) {
            deleteWhere { ((MEETING_ID eq meetingId.long)) }
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getPublicMeetingsFlow(): Flow<DatabaseMeeting> = channelFlow {
        newSuspendedTransaction(db = db) {
            select { VISIBILITY eq DatabaseMeeting.Visibility.Public }
                .asFlow()
                .map { it.toDatabaseMeeting() }
                .collect(::send)
        }
    }

    private fun ResultRow.toDatabaseMeeting(): DatabaseMeeting {
        val avatarId = this[AVATAR_ID]
        val avatarHash = this[AVATAR_HASH]
        val avatarIdentity = if (avatarId != null && avatarHash != null) {
            FileIdentity(FileId(avatarId), AccessHash(avatarHash))
        } else null

        return DatabaseMeeting(
            identity = MeetingIdentity(
                meetingId = MeetingId(this[MEETING_ID]),
                accessHash = AccessHash(this[ACCESS_HASH])
            ),
            creatorId = UserId(this[CREATOR_ID]),
            date = DateOrTime.parse(this[DATE]),
            location = Location(this[LATITUDE], this[LONGITUDE]),
            description = this[DESCRIPTION],
            title = this[TITLE],
            avatarIdentity = avatarIdentity,
            visibility = this[VISIBILITY]
        )
    }
}
