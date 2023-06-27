package app.meetacy.backend.database.integration.location.stream

import app.meetacy.backend.database.location.UsersLocationsStorage
import app.meetacy.backend.types.location.Location
import app.meetacy.backend.types.location.LocationSnapshot
import app.meetacy.backend.types.user.UserId
import app.meetacy.backend.usecase.location.stream.LocationFlowStorage
import org.jetbrains.exposed.sql.Database

class DatabaseLocationFlowStorageUnderlying(db: Database) : LocationFlowStorage.Underlying {
    private val table = UsersLocationsStorage(db)

    override suspend fun setLocation(userId: UserId, location: Location) {
        table.saveLocation(userId, location)
    }

    override suspend fun getLocation(userId: UserId): LocationSnapshot? {
        return table.getLocation(userId)
    }
}
