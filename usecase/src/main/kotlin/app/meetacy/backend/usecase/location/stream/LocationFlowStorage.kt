package app.meetacy.backend.usecase.location.stream

import app.meetacy.backend.types.datetime.DateTime
import app.meetacy.backend.types.location.Location
import app.meetacy.backend.types.location.TimedLocation
import app.meetacy.backend.types.user.UserId
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class LocationFlowStorage(private val underlying: Underlying) {
    private val allLocationUpdates = MutableSharedFlow<Update>()

    suspend fun setLocation(userId: UserId, location: Location) {
        allLocationUpdates.emit(Update(userId, location))
        underlying.setLocation(userId, location)
    }

    fun locationFlow(userId: UserId): Flow<TimedLocation> = channelFlow {
        val channel = Channel<Update>()

        launch {
            allLocationUpdates
                .filter { it.userId == userId }
                .collect(channel::send)
        }

        val defaultLocation = underlying.getLocation(userId)
        if (defaultLocation != null) {
            send(defaultLocation)
        }

        for (element in channel) {
            send(
                element = TimedLocation(
                    location = element.location,
                    capturedAt = DateTime.now()
                )
            )
        }
    }

    interface Underlying {
        suspend fun setLocation(userId: UserId, location: Location)
        suspend fun getLocation(userId: UserId): TimedLocation?
    }

    private data class Update(
        val userId: UserId,
        val location: Location
    )
}
