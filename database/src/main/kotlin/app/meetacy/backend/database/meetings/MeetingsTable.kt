@file:Suppress("PrivatePropertyName")

package app.meetacy.backend.database.meetings

import app.meetacy.backend.database.types.DatabaseMeeting
import app.meetacy.backend.types.DATA_MAX_LIMIT
import app.meetacy.backend.types.DESCRIPTION_MAX_LIMIT
import app.meetacy.backend.types.HASH_LENGTH
import app.meetacy.backend.types.TITLE_MAX_LIMIT
import app.meetacy.backend.types.access.AccessHash
import app.meetacy.backend.types.annotation.UnsafeConstructor
import app.meetacy.backend.types.datetime.Date
import app.meetacy.backend.types.file.FileId
import app.meetacy.backend.types.file.FileIdentity
import app.meetacy.backend.types.location.Location
import app.meetacy.backend.types.meeting.IdMeeting
import app.meetacy.backend.types.meeting.MeetingIdentity
import app.meetacy.backend.types.user.UserId
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

class MeetingsTable(private val db: Database) : Table() {
    private val ID_MEETING = long("ID_MEETING").autoIncrement()
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

    override val primaryKey = PrimaryKey(ID_MEETING)

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
    ): IdMeeting =
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
            }[ID_MEETING]
            return@newSuspendedTransaction IdMeeting(meetingId)
        }

    suspend fun getMeeting(id: IdMeeting): DatabaseMeeting =
        getMeetingOrNull(id) ?: error("Cannot find a meeting with id $id")

    suspend fun getMeetingOrNull(id: IdMeeting): DatabaseMeeting? =
        getMeetingsOrNull(listOf(id)).first()

    suspend fun getMeetingsOrNull(idMeetings: List<IdMeeting>): List<DatabaseMeeting?> =
        newSuspendedTransaction(db = db) {
            val rawMeetingIds = idMeetings.map { it.long }

            val foundMeetings = select { ID_MEETING inList rawMeetingIds }
                .map { it.toDatabaseMeeting() }
                .associateBy { it.id }

            return@newSuspendedTransaction idMeetings.map { foundMeetings[it] }
        }

    suspend fun getCreatorMeetings(creatorId: UserId): List<IdMeeting> =
        newSuspendedTransaction(db = db) {
            val result = select { (CREATOR_ID eq creatorId.long) }
                .map { statement -> statement.toDatabaseMeeting() }
            return@newSuspendedTransaction result.filter { it.creatorId == creatorId }.map { it.id }
        }

    suspend fun addAvatar(idMeeting: IdMeeting, avatarIdentity: FileIdentity) =
        newSuspendedTransaction(db = db) {
            update({ ID_MEETING eq idMeeting.long }) { statement ->
                statement[AVATAR_ID] = avatarIdentity.fileId.long
                statement[AVATAR_HASH] = avatarIdentity.accessHash.string
            }
        }

    suspend fun deleteAvatar(idMeeting: IdMeeting) =
        newSuspendedTransaction(db = db) {
            update({ ID_MEETING eq idMeeting.long }) { statement ->
                statement[AVATAR_ID] = null
                statement[AVATAR_HASH] = null
            }
        }

    suspend fun deleteMeeting(idMeeting: IdMeeting) =
        newSuspendedTransaction(db = db) {
            deleteWhere { ((ID_MEETING eq idMeeting.long)) }
        }

    suspend fun editMeeting(
        idMeeting: IdMeeting,
        avatarId: FileIdentity?,
        deleteAvatar: Boolean,
        title: String?,
        description: String?,
        location: Location?,
        date: Date?,
        visibility: DatabaseMeeting.Visibility?
    ) = newSuspendedTransaction(db = db) {
        if (avatarId == null && deleteAvatar) deleteAvatar(idMeeting)
        if (avatarId != null) addAvatar(idMeeting, avatarId)
        update({ ID_MEETING eq idMeeting.long }) { statement ->
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
    private fun ResultRow.toDatabaseMeeting(): DatabaseMeeting {
        val avatarId = this[AVATAR_ID]
        val avatarHash = this[AVATAR_HASH]
        val avatarIdentity = if (avatarId != null && avatarHash != null) {
            FileIdentity(FileId(avatarId), AccessHash(avatarHash))
        } else null

        return DatabaseMeeting(
            identity = MeetingIdentity(
                idMeeting = IdMeeting(this[ID_MEETING]),
                accessHash = AccessHash(this[ACCESS_HASH])
            ),
            creatorId = UserId(this[CREATOR_ID]),
            date = Date(this[DATE]),
            location = Location(this[LATITUDE], this[LONGITUDE]),
            description = this[DESCRIPTION],
            title = this[TITLE],
            avatarIdentity = avatarIdentity,
            visibility = this[VISIBILITY]
        )
    }
}
