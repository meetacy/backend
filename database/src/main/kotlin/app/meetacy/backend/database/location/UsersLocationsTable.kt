@file:Suppress("PrivatePropertyName")

package app.meetacy.backend.database.location

import app.meetacy.backend.types.DATE_TIME_MAX_LIMIT
import app.meetacy.backend.types.annotation.UnsafeConstructor
import app.meetacy.backend.types.datetime.DateTime
import app.meetacy.backend.types.location.Location
import app.meetacy.backend.types.location.TimedLocation
import app.meetacy.backend.types.user.UserId
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

class UsersLocationsTable(private val db: Database) : Table() {
    private val USER_ID = long("USER_ID")
    private val LATITUDE = double("LATITUDE")
    private val LONGITUDE = double("LONGITUDE")
    private val UPDATED_TIME = varchar("UPDATED_TIME", DATE_TIME_MAX_LIMIT)

    init {
        transaction(db) {
            SchemaUtils.create(this@UsersLocationsTable)
        }
    }

    suspend fun saveLocation(
        userId: UserId,
        location: Location,
        capturedAt: DateTime = DateTime.now()
    ) {
        saveLocation(
            userId = userId,
            location = TimedLocation(location, capturedAt)
        )
    }

    suspend fun saveLocation(
        userId: UserId,
        location: TimedLocation
    ) {
        newSuspendedTransaction(db = db) {
            if (select { USER_ID eq userId.long }.any()) {
                update { statement ->
                    statement[LATITUDE] = location.location.latitude
                    statement[LONGITUDE] = location.longitude
                    statement[UPDATED_TIME] = location.capturedAt.iso8601
                }
            } else {
                insert { statement ->
                    statement[USER_ID] = userId.long
                    statement[LATITUDE] = location.latitude
                    statement[LONGITUDE] = location.longitude
                    statement[UPDATED_TIME] = location.capturedAt.iso8601
                }
            }
        }
    }

    suspend fun getLocation(userId: UserId): TimedLocation? = newSuspendedTransaction {
        select { USER_ID eq userId.long }.singleOrNull()?.toTimedLocation()
    }

    @OptIn(UnsafeConstructor::class)
    private fun ResultRow.toTimedLocation(): TimedLocation {
        return TimedLocation(
            latitude = this[LATITUDE],
            longitude = this[LONGITUDE],
            capturedAt = DateTime(this[UPDATED_TIME])
        )
    }
}
