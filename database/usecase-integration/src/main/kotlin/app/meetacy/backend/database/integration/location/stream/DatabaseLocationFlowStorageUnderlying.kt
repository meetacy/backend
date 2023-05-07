package app.meetacy.backend.database.integration.location.stream

import app.meetacy.backend.database.location.UsersLocationsTable
import app.meetacy.backend.types.location.Location
import app.meetacy.backend.types.location.TimedLocation
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.location.stream.LocationFlowStorage
import org.jetbrains.exposed.sql.Database

class DatabaseLocationFlowStorageUnderlying(db: Database) : LocationFlowStorage.Underlying {
    private val table = UsersLocationsTable(db)

    override suspend fun setLocation(userId: UserId, location: Location) {
        table.saveLocation(userId, location)
    }

    override suspend fun getLocation(userId: UserId): TimedLocation? {
        return table.getLocation(userId)
    }
}
