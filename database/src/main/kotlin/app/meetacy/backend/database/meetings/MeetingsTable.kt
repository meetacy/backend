@file:Suppress("PrivatePropertyName")

package app.meetacy.backend.database.meetings

import app.meetacy.backend.database.types.*
import app.meetacy.backend.types.*
import org.jetbrains.exposed.sql.*
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

    override val primaryKey = PrimaryKey(MEETING_ID)

    init {
        transaction(db) {
            SchemaUtils.create(this@MeetingsTable)
        }
    }

    fun addMeeting(
        accessHash: AccessHash,
        creatorId: UserId,
        date: Date,
        location: Location,
        title: String?,
        description: String?
    ): MeetingId =
        transaction(db) {
            val meetingId = insert { statement ->
                statement[ACCESS_HASH] = accessHash.string
                statement[CREATOR_ID] = creatorId.long
                statement[DATE] = date.iso8601
                statement[LATITUDE] = location.latitude
                statement[LONGITUDE] = location.longitude
                statement[TITLE] = title
                statement[DESCRIPTION] = description
            }[MEETING_ID]
            return@transaction MeetingId(meetingId)
        }

    fun getMeetingOrNull(id: MeetingId): DatabaseMeeting? =
        transaction(db) {
            val result = select { (MEETING_ID eq id.long) }
                .map { statement -> statement.toDatabaseMeeting() }
            return@transaction result.filter { it.id == id }
        }.firstOrNull()

    fun getMeetingsOrNull(meetingIds: List<MeetingId>): List<DatabaseMeeting?> = transaction(db) {
        val rawMeetingIds = meetingIds.map { it.long }

        val foundMeetings = select { MEETING_ID inList rawMeetingIds }
            .map { it.toDatabaseMeeting() }
            .associateBy { it.id }

        return@transaction meetingIds.map { foundMeetings[it] }
    }

    fun getMeetingCreator(creatorId: UserId): List<MeetingId> =
        transaction(db) {
            val result = select { (CREATOR_ID eq creatorId.long) }
                .map { statement -> statement.toDatabaseMeeting() }
            return@transaction result.filter { it.creatorId == creatorId }.map { it.id }
        }

    private fun ResultRow.toDatabaseMeeting() = DatabaseMeeting(
        id = MeetingId(this[MEETING_ID]),
        accessHash = AccessHash(this[ACCESS_HASH]),
        creatorId = UserId(this[CREATOR_ID]),
        date = Date(this[DATE]),
        location = Location(this[LATITUDE], this[LONGITUDE]),
        description = this[DESCRIPTION],
        title = this[TITLE]
    )
}
