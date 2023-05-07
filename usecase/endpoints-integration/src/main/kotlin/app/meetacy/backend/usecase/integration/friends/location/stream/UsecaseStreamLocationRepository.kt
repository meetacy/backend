package app.meetacy.backend.usecase.integration.friends.location.stream

import app.meetacy.backend.endpoint.friends.location.stream.StreamLocationRepository
import app.meetacy.backend.endpoint.types.UserOnMap
import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.location.Location
import app.meetacy.backend.usecase.integration.types.mapToEndpoint
import app.meetacy.backend.usecase.location.stream.FriendsLocationStreamingUsecase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

class UsecaseStreamLocationRepository(
    private val usecase: FriendsLocationStreamingUsecase
) : StreamLocationRepository {
    override suspend fun stream(
        accessIdentity: AccessIdentity,
        selfLocation: Flow<Location>,
        collector: FlowCollector<UserOnMap>
    ) {
        usecase.stream(accessIdentity, selfLocation) { user ->
            collector.emit(user.mapToEndpoint())
        }
    }
}
