package app.meetacy.backend.usecase.integration.meetings.get

import app.meetacy.backend.endpoint.meetings.get.GetMeetingRepository
import app.meetacy.backend.endpoint.meetings.get.GetMeetingResult
import app.meetacy.backend.endpoint.meetings.get.GetMeetingsParam
import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.meeting.MeetingIdentity
import app.meetacy.backend.usecase.integration.types.mapToEndpoint
import app.meetacy.backend.usecase.meetings.get.GetMeetingUsecase

class UsecaseGetMeetingRepository(
    private val usecase: GetMeetingUsecase
) : GetMeetingRepository {

    // todo некоторые принимают сразу параметры а некоторые *Params из endpoints
    override suspend fun getMeeting(params: GetMeetingsParam): GetMeetingResult =

        when (val result = usecase.getMeeting(params.token.type(), params.meetingId.type())) {
            is GetMeetingUsecase.Result.Success ->
                GetMeetingResult.Success(
                    result.meeting.mapToEndpoint()
                )

            GetMeetingUsecase.Result.MeetingNotFound ->
                GetMeetingResult.MeetingNotFound

            GetMeetingUsecase.Result.TokenInvalid ->
                GetMeetingResult.InvalidAccessIdentity
        }

}
