package app.meetacy.backend.feature.auth.usecase.integration.meetings.get

import app.meetacy.backend.endpoint.meetings.get.GetMeetingRepository
import app.meetacy.backend.endpoint.meetings.get.GetMeetingResult
import app.meetacy.backend.feature.auth.usecase.integration.types.mapToEndpoint
import app.meetacy.backend.feature.auth.usecase.meetings.get.GetMeetingUsecase
import app.meetacy.backend.types.serializable.access.AccessIdentity
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.meeting.MeetingIdentity
import app.meetacy.backend.types.serializable.meeting.type

class UsecaseGetMeetingRepository(
    private val usecase: GetMeetingUsecase
) : GetMeetingRepository {

    override suspend fun getMeeting(
        accessIdentity: AccessIdentity,
        meetingIdentity: MeetingIdentity
    ): GetMeetingResult =

        when (val result = usecase.getMeeting(accessIdentity.type(), meetingIdentity.type()!!)) {
            is GetMeetingUsecase.Result.Success ->
                GetMeetingResult.Success(result.meeting.mapToEndpoint())

            GetMeetingUsecase.Result.MeetingNotFound ->
                GetMeetingResult.MeetingNotFound

            GetMeetingUsecase.Result.TokenInvalid ->
                GetMeetingResult.InvalidAccessIdentity
        }

}
