package app.meetacy.backend.feature.meetings.usecase.integration.get

import app.meetacy.backend.feature.meetings.endpoints.get.GetMeetingRepository
import app.meetacy.backend.feature.meetings.endpoints.get.GetMeetingResult
import app.meetacy.backend.types.serializable.access.AccessIdentity
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.meetings.MeetingIdentity
import app.meetacy.backend.types.serializable.meetings.type
import app.meetacy.backend.feature.meetings.usecase.get.GetMeetingUsecase

class UsecaseGetMeetingRepository(
    private val usecase: GetMeetingUsecase
) : GetMeetingRepository {

    override suspend fun getMeeting(
        accessIdentity: AccessIdentity,
        meetingIdentity: MeetingIdentity
    ): GetMeetingResult =

        when (val result = usecase.getMeeting(accessIdentity.type(), meetingIdentity.type()!!)) {
            is GetMeetingUsecase.Result.Success ->
                GetMeetingResult.Success(result.meeting.type())

            GetMeetingUsecase.Result.MeetingNotFound ->
                GetMeetingResult.MeetingNotFound

            GetMeetingUsecase.Result.TokenInvalid ->
                GetMeetingResult.InvalidAccessIdentity
        }

}
