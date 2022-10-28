package app.meetacy.backend.usecase.integration.meetings.list

import app.meetacy.backend.types.AccessIdentity
import app.meetacy.backend.endpoint.meetings.list.ListMeetingsResult
import app.meetacy.backend.endpoint.meetings.list.MeetingsListRepository
import app.meetacy.backend.usecase.integration.types.mapToEndpoint
import app.meetacy.backend.usecase.meetings.list.GetMeetingsListUsecase

class UsecaseMeetingsListRepository(
    private val usecase: GetMeetingsListUsecase
): MeetingsListRepository {
    override suspend fun getList(accessIdentity: AccessIdentity): ListMeetingsResult =
        when(val result = usecase.getMeetingsList(accessIdentity)) {
            is GetMeetingsListUsecase.Result.Success ->
                ListMeetingsResult.Success(
                    result.meetings.map { meetingView ->
                        meetingView.mapToEndpoint()
                    }
                )
            GetMeetingsListUsecase.Result.TokenInvalid ->
                ListMeetingsResult.TokenInvalid
        }
}
