@file:Suppress("PrivatePropertyName")

package app.meetacy.backend.database.location

import app.meetacy.backend.database.location.UsersLocationsTable.LATITUDE
import app.meetacy.backend.database.location.UsersLocationsTable.LONGITUDE
import app.meetacy.backend.database.location.UsersLocationsTable.UPDATED_TIME
import app.meetacy.backend.database.location.UsersLocationsTable.USER_ID
import app.meetacy.backend.types.DATE_TIME_MAX_LIMIT
import app.meetacy.backend.types.annotation.UnsafeConstructor
import app.meetacy.backend.types.datetime.DateTime
import app.meetacy.backend.types.location.Location
import app.meetacy.backend.types.location.LocationSnapshot
import app.meetacy.backend.types.user.UserId
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

object UsersLocationsTable : Table() {
    val USER_ID = long("USER_ID")
    val LATITUDE = double("LATITUDE")
    val LONGITUDE = double("LONGITUDE")
    val UPDATED_TIME = varchar("UPDATED_TIME", DATE_TIME_MAX_LIMIT)
}

class UsersLocationsStorage(private val db: Database) {

    suspend fun saveLocation(
        userId: UserId,
        location: Location,
        capturedAt: DateTime = DateTime.now()
    ) {
        saveLocation(
            userId = userId,
            location = LocationSnapshot(location, capturedAt)
        )
    }

    suspend fun saveLocation(
        userId: UserId,
        location: LocationSnapshot
    ) {
        newSuspendedTransaction(Dispatchers.IO, db) {
            if (UsersLocationsTable.select { USER_ID eq userId.long }.any()) {
                UsersLocationsTable.update { statement ->
                    statement[LATITUDE] = location.location.latitude
                    statement[LONGITUDE] = location.longitude
                    statement[UPDATED_TIME] = location.capturedAt.iso8601
                }
            } else {
                UsersLocationsTable.insert { statement ->
                    statement[USER_ID] = userId.long
                    statement[LATITUDE] = location.latitude
                    statement[LONGITUDE] = location.longitude
                    statement[UPDATED_TIME] = location.capturedAt.iso8601
                }
            }
        }
    }

    suspend fun getLocation(userId: UserId): LocationSnapshot? = newSuspendedTransaction {
        UsersLocationsTable.select { USER_ID eq userId.long }.singleOrNull()?.toLocationSnapshot()
    }

    @OptIn(UnsafeConstructor::class)
    private fun ResultRow.toLocationSnapshot(): LocationSnapshot {
        return LocationSnapshot(
            latitude = this[LATITUDE],
            longitude = this[LONGITUDE],
            capturedAt = DateTime(this[UPDATED_TIME])
        )
    }
}
