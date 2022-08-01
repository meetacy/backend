package app.meetacy.backend.usecase.integration.meetings

import app.meetacy.backend.domain.AccessToken
import app.meetacy.backend.endpoint.meetings.list.ListMeetingsResult
import app.meetacy.backend.endpoint.meetings.list.MeetingsProvider
import app.meetacy.backend.usecase.integration.types.mapToEndpoint
import app.meetacy.backend.usecase.meetings.GetMeetingsListUsecase

private class GetListMeetingsIntegration(
    private val usecase: GetMeetingsListUsecase
): MeetingsProvider {
    override suspend fun getList(accessToken: AccessToken): ListMeetingsResult =
        when(val result = usecase.getMeetingsList(accessToken)) {
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
fun usecaseGetListMeetingsRepository(usecase: GetListMeetingsUsecase): MeetingsProvider =
    GetListMeetingsIntegration(usecase)