@file:Suppress("PrivatePropertyName")

package app.meetacy.backend.database.meetings

import app.meetacy.backend.database.types.DatabaseMeeting
import app.meetacy.backend.types.DATE_MAX_LIMIT
import app.meetacy.backend.types.DESCRIPTION_MAX_LIMIT
import app.meetacy.backend.types.HASH_LENGTH
import app.meetacy.backend.types.TITLE_MAX_LIMIT
import app.meetacy.backend.types.access.AccessHash
import app.meetacy.backend.types.annotation.UnsafeConstructor
import app.meetacy.backend.types.datetime.Date
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
    private val MEETING_ID = long("ID_MEETING").autoIncrement()
    private val ACCESS_HASH = varchar("ACCESS_HASH", length = HASH_LENGTH)
    private val CREATOR_ID = long("CREATOR_ID")
    private val DATE = varchar("DATE", length = DATE_MAX_LIMIT)
    private val LATITUDE = double("LATITUDE")
    private val LONGITUDE = double("LONGITUDE")
    private val TITLE = varchar("TITLE", length = TITLE_MAX_LIMIT).nullable()
    private val DESCRIPTION = varchar("DESCRIPTION", length = DESCRIPTION_MAX_LIMIT).nullable()
    private val AVATAR_ID = long("AVATAR_ID").nullable()
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
        date: Date,
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

    suspend fun getCreatorMeetings(creatorId: UserId): List<MeetingId> =
        newSuspendedTransaction(db = db) {
            val result = select { (CREATOR_ID eq creatorId.long) }
                .map { statement -> statement.toDatabaseMeeting() }
            return@newSuspendedTransaction result.filter { it.creatorId == creatorId }.map { it.id }
        }

    suspend fun deleteMeeting(meetingId: MeetingId) =
        newSuspendedTransaction(db = db) {
            deleteWhere { ((MEETING_ID eq meetingId.long)) }
        }

    suspend fun editMeeting(
        meetingId: MeetingId,
        avatarId: FileIdentity?,
        deleteAvatar: Boolean,
        title: String?,
        description: String?,
        location: Location?,
        date: Date?,
        visibility: DatabaseMeeting.Visibility?
    ) = newSuspendedTransaction(db = db) {
        if (avatarId == null && deleteAvatar) deleteAvatar(meetingId)
        if (avatarId != null) addAvatar(meetingId, avatarId)
        update({ MEETING_ID eq meetingId.long }) { statement ->
            title?.let { statement[TITLE] = it }
            description?.let { statement[DESCRIPTION] = it }
            location?.let {
                statement[LATITUDE] = it.latitude
                statement[LONGITUDE] = it.longitude
            }
            date?.let { statement[DATE]= it.iso8601 }
            visibility?.let { statement[VISIBILITY] = it }
        }
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
