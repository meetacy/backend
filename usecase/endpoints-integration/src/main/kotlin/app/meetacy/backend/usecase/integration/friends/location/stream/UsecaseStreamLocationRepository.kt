package app.meetacy.backend.usecase.integration.friends.location.stream

import app.meetacy.backend.endpoint.friends.location.stream.StreamLocationRepository
import app.meetacy.backend.endpoint.types.user.UserLocationSnapshot
import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.location.Location
import app.meetacy.backend.usecase.integration.types.mapToEndpoint
import app.meetacy.backend.usecase.location.stream.FriendsLocationStreamingUsecase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import app.meetacy.backend.usecase.types.UserLocationSnapshot as UsecaseUserLocationSnapshot

class UsecaseStreamLocationRepository(
    private val usecase: FriendsLocationStreamingUsecase
) : StreamLocationRepository {
    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun stream(
        accessIdentity: AccessIdentity,
        selfLocation: Flow<Location>,
        channel: SendChannel<UserLocationSnapshot>
    ) = coroutineScope {
        val usecaseChannel = produce {
            usecase.stream(
                accessIdentity,
                selfLocation,
                channel = this.channel
            )
        }

        for (element in usecaseChannel) {
            channel.send(element.mapToEndpoint())
        }
    }
}
