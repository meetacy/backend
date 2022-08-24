@file:Suppress("PrivatePropertyName")

package app.meetacy.backend.database

import app.meetacy.backend.types.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class MeetingsTable(private val db: Database) : Table() {
    private val MEETING_ID = long("MEETING_ID")
    private val ACCESS_HASH = varchar("ACCESS_HASH", length = 256)
    private val CREATOR_ID = long("CREATOR_ID")
    private val DATE = varchar("DATE", length = 50)
    private val LATITUDE = double("LATITUDE")
    private val LONGITUDE = double("LONGITUDE")
    private val TITLE = varchar("TITLE", length = 100)
    private val DESCRIPTION = varchar("DESCRIPTION", length = 400)


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
            val id = MeetingId(
                select { MEETING_ID.isNotNull() }.count()
            )
            insert { statement ->
                statement[MEETING_ID] = id.long
                statement[ACCESS_HASH] = accessHash.string
                statement[CREATOR_ID] = creatorId.long
                statement[DATE] = date.iso8601
                statement[LATITUDE] = location.latitude
                statement[LONGITUDE] = location.longitude
                statement[TITLE] = title ?: "null"
                statement[DESCRIPTION] = description ?: "null"
            }
            return@transaction id
        }
}
