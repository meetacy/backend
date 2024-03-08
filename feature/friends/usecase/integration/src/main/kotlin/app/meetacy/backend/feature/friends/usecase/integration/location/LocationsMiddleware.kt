package app.meetacy.backend.feature.friends.usecase.integration.location

import app.meetacy.backend.feature.friends.database.location.UsersLocationsStorage
import app.meetacy.backend.feature.friends.usecase.location.LocationsMiddleware
import app.meetacy.backend.types.datetime.DateTime
import app.meetacy.backend.types.location.Location
import app.meetacy.backend.types.location.LocationSnapshot
import app.meetacy.backend.types.users.UserId
import app.meetacy.di.builder.DIBuilder
import org.jetbrains.exposed.sql.Database

fun DIBuilder.locationMiddleware() {
    val locationMiddleware by singleton<LocationsMiddleware> {
        val storage = object : LocationsMiddleware.Storage {
            private val database: Database by getting
            private val table = UsersLocationsStorage(database)

            override suspend fun setLocation(userId: UserId, location: Location) {
                table.saveLocation(userId, location)
            }

            override suspend fun getLocation(userId: UserId, from: DateTime): LocationSnapshot? {
                return table.getLocation(userId, from)
            }
        }
        LocationsMiddleware(storage)
    }
}
