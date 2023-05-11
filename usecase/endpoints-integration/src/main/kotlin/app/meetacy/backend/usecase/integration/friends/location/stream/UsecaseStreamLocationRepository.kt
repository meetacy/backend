package app.meetacy.backend.usecase.integration.friends.location.stream

import app.meetacy.backend.endpoint.friends.location.stream.StreamLocationRepository
import app.meetacy.backend.endpoint.types.UserLocationSnapshot
import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.location.Location
import app.meetacy.backend.usecase.integration.types.mapToEndpoint
import app.meetacy.backend.usecase.location.stream.FriendsLocationStreamingUsecase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import app.meetacy.backend.usecase.types.UserLocationSnapshot as UsecaseUserLocationSnapshot

class UsecaseStreamLocationRepository(
    private val usecase: FriendsLocationStreamingUsecase
) : StreamLocationRepository {
    override suspend fun stream(
        accessIdentity: AccessIdentity,
        selfLocation: Flow<Location>,
        channel: SendChannel<UserLocationSnapshot>
    ) = coroutineScope {
        val upstream = Channel<UsecaseUserLocationSnapshot>()
        launch {
            usecase.stream(accessIdentity, selfLocation, upstream)
        }
        for (element in upstream) {
            channel.send(element.mapToEndpoint())
        }
    }
}
