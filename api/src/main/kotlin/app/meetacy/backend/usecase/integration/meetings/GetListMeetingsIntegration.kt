package app.meetacy.backend.usecase.integration.meetings

import app.meetacy.backend.domain.AccessToken
import app.meetacy.backend.endpoint.meetings.list.ListMeetingsResult
import app.meetacy.backend.endpoint.meetings.list.MeetingsProvider
import app.meetacy.backend.endpoint.types.Meeting
import app.meetacy.backend.endpoint.types.User
import app.meetacy.backend.usecase.meetings.GetListMeetingsUsecase

private class GetListMeetingsIntegration(
    private val usecase: GetListMeetingsUsecase
): MeetingsProvider {
    override suspend fun getList(accessToken: AccessToken): ListMeetingsResult =
        when(val result = usecase.getListMeetings(accessToken)) {
            is GetListMeetingsUsecase.Result.Success ->
                ListMeetingsResult.Success(
                    result.meetings.map {
                            meetingView -> Meeting(
                        id = meetingView.id,
                        accessHash = meetingView.accessHash,
                        creator = User(
                            id = meetingView.creator.id,
                            accessHash = meetingView.creator.accessHash,
                            nickname = meetingView.creator.nickname,
                            email = meetingView.creator.email,
                            emailVerified = meetingView.creator.emailVerified
                        ),
                        date = meetingView.date,
                        location = meetingView.location,
                        title = meetingView.title,
                        description = meetingView.description,
                        participantsCount = meetingView.participantsCount,
                        isParticipating = meetingView.isParticipating
                            )
                    }
                )
            GetListMeetingsUsecase.Result.TokenInvalid ->
                ListMeetingsResult.TokenInvalid
        }
}
fun usecaseGetListMeetingsRepository(usecase: GetListMeetingsUsecase): MeetingsProvider =
    GetListMeetingsIntegration(usecase)