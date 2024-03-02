@file:Suppress("PrivatePropertyName")

package app.meetacy.backend.feature.friends.database.location

import app.meetacy.backend.feature.friends.database.location.UsersLocationsTable.LATITUDE
import app.meetacy.backend.feature.friends.database.location.UsersLocationsTable.LONGITUDE
import app.meetacy.backend.feature.friends.database.location.UsersLocationsTable.UPDATED_TIME_MILLIS
import app.meetacy.backend.feature.friends.database.location.UsersLocationsTable.USER_ID
import app.meetacy.backend.types.datetime.DateTime
import app.meetacy.backend.types.location.Location
import app.meetacy.backend.types.location.LocationSnapshot
import app.meetacy.backend.types.users.UserId
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

object UsersLocationsTable : Table() {
    val USER_ID = long("USER_ID")
    val LATITUDE = double("LATITUDE")
    val LONGITUDE = double("LONGITUDE")
    val UPDATED_TIME_MILLIS = long("UPDATED_TIME_MILLIS")
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
                UsersLocationsTable.update(
                    where = { USER_ID eq userId.long }
                ) { statement ->
                    statement[LATITUDE] = location.location.latitude
                    statement[LONGITUDE] = location.longitude
                    statement[UPDATED_TIME_MILLIS] = location.capturedAt.epochMillis
                }
            } else {
                UsersLocationsTable.insert { statement ->
                    statement[USER_ID] = userId.long
                    statement[LATITUDE] = location.latitude
                    statement[LONGITUDE] = location.longitude
                    statement[UPDATED_TIME_MILLIS] = location.capturedAt.epochMillis
                }
            }
        }
    }

    suspend fun getLocation(
        userId: UserId,
        from: DateTime
    ): LocationSnapshot? = newSuspendedTransaction(Dispatchers.IO, db) {
        UsersLocationsTable.select {
            (USER_ID eq userId.long) and (UPDATED_TIME_MILLIS greater from.epochMillis)
        }.firstOrNull()?.toLocationSnapshot()
    }

    private fun ResultRow.toLocationSnapshot(): LocationSnapshot {
        return LocationSnapshot(
            latitude = this[LATITUDE],
            longitude = this[LONGITUDE],
            capturedAt = DateTime.ofEpochMillis(this[UPDATED_TIME_MILLIS])
        )
    }
}
