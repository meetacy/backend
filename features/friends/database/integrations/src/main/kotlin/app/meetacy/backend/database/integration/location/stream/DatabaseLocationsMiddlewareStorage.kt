package app.meetacy.backend.database.integration.location.stream

import app.meetacy.backend.database.location.UsersLocationsStorage
import app.meetacy.backend.types.location.Location
import app.meetacy.backend.types.location.LocationSnapshot
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.location.stream.LocationsMiddleware
import org.jetbrains.exposed.sql.Database

@Suppress("FunctionName")
fun DatabaseLocationsMiddleware(db: Database): LocationsMiddleware {
    return LocationsMiddleware(
        storage = DatabaseLocationsMiddlewareStorage(db)
    )
}

class DatabaseLocationsMiddlewareStorage(db: Database) : LocationsMiddleware.Storage {
    private val table = UsersLocationsStorage(db)

    override suspend fun setLocation(userId: UserId, location: Location) {
        table.saveLocation(userId, location)
    }

    override suspend fun getLocation(userId: UserId): LocationSnapshot? {
        return table.getLocation(userId)
    }
}
