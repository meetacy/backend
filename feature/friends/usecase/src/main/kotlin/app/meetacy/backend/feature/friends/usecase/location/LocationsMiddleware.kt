package app.meetacy.backend.feature.friends.usecase.location

import app.meetacy.backend.types.datetime.DateTime
import app.meetacy.backend.types.location.Location
import app.meetacy.backend.types.location.LocationSnapshot
import app.meetacy.backend.types.users.UserId
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class LocationsMiddleware(private val storage: Storage) {
    private val allLocationUpdates = MutableSharedFlow<Update>()

    suspend fun setLocation(userId: UserId, location: Location) {
        storage.setLocation(userId, location)
        allLocationUpdates.emit(Update(userId, location))
    }

    fun locationFlow(userId: UserId): Flow<LocationSnapshot> = channelFlow {
        val channel = Channel<Update>()

        launch {
            allLocationUpdates
                .filter { it.userId == userId }
                .collect(channel::send)
        }

        val defaultLocation = storage.getLocation(userId)
        if (defaultLocation != null) {
            send(defaultLocation)
        }

        for (element in channel) {
            send(
                element = LocationSnapshot(
                    location = element.location,
                    capturedAt = DateTime.now()
                )
            )
        }
    }

    interface Storage {
        suspend fun setLocation(userId: UserId, location: Location)
        suspend fun getLocation(userId: UserId): LocationSnapshot?
    }

    private data class Update(
        val userId: UserId,
        val location: Location
    )
}
