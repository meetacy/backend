package app.meetacy.backend.usecase.integration.meetings.get

import app.meetacy.backend.endpoint.meetings.get.GetMeetingRepository
import app.meetacy.backend.endpoint.meetings.get.GetMeetingResult
import app.meetacy.backend.types.AccessHash
import app.meetacy.backend.types.AccessToken
import app.meetacy.backend.types.MeetingId
import app.meetacy.backend.usecase.integration.types.mapToEndpoint
import app.meetacy.backend.usecase.meetings.GetMeetingUsecase

class UsecaseGetMeetingRepository(
    private val usecase: GetMeetingUsecase
) : GetMeetingRepository {

    override suspend fun getMeeting(
        accessToken: AccessToken,
        meetingId: MeetingId,
        meetingAccessHash: AccessHash
    ): GetMeetingResult =

        when (val result = usecase.getMeeting(accessToken, meetingId, meetingAccessHash)) {
            is GetMeetingUsecase.Result.Success ->
                GetMeetingResult.Success(
                    result.meeting.mapToEndpoint()
                )

            GetMeetingUsecase.Result.MeetingNotFound ->
                GetMeetingResult.MeetingNotFound

            GetMeetingUsecase.Result.TokenInvalid ->
                GetMeetingResult.TokenInvalid
        }

}
