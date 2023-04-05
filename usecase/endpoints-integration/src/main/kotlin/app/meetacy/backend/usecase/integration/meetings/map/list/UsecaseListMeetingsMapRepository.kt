package app.meetacy.backend.usecase.integration.meetings.map.list

import app.meetacy.backend.endpoint.meetings.map.list.ListMeetingsMapRepository
import app.meetacy.backend.endpoint.meetings.map.list.ListMeetingsResult
import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.serialization.location.LocationSerializable
import app.meetacy.backend.usecase.integration.types.mapToEndpoint
import app.meetacy.backend.usecase.meetings.map.list.ListMeetingsMapUsecase
import app.meetacy.backend.usecase.meetings.map.list.ListMeetingsMapUsecase.Result
import app.meetacy.backend.usecase.types.MeetingView

class UsecaseListMeetingsMapRepository(
    private val usecase: ListMeetingsMapUsecase
) : ListMeetingsMapRepository {
    override suspend fun list(
        accessIdentity: AccessIdentity,
        location: LocationSerializable
    ): ListMeetingsResult =
        when (val result = usecase.getMeetingsList(accessIdentity, location.type())) {
            is Result.InvalidAccessIdentity -> ListMeetingsResult.InvalidIdentity
            is Result.Success -> ListMeetingsResult.Success(
                meetings = result.meetings.map(MeetingView::mapToEndpoint)
            )
        }
}
