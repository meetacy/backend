package app.meetacy.backend.usecase.integration.meetings.participate

import app.meetacy.backend.endpoint.meetings.participate.ParticipateMeetingRepository
import app.meetacy.backend.endpoint.meetings.participate.ParticipateMeetingResult
import app.meetacy.backend.types.AccessToken
import app.meetacy.backend.types.MeetingIdentity
import app.meetacy.backend.usecase.meetings.ParticipateMeetingUsecase

class UsecaseParticipateMeetingRepository(
    private val usecase: ParticipateMeetingUsecase
) : ParticipateMeetingRepository {

    override suspend fun participateMeeting(
        meetingIdentity: MeetingIdentity,
        accessToken: AccessToken
    ): ParticipateMeetingResult =
        when (usecase.participateMeeting(meetingIdentity, accessToken)) {
            is ParticipateMeetingUsecase.Result.Success ->
                ParticipateMeetingResult.Success

            ParticipateMeetingUsecase.Result.MeetingNotFound ->
                ParticipateMeetingResult.MeetingNotFound

            ParticipateMeetingUsecase.Result.TokenInvalid ->
                ParticipateMeetingResult.TokenInvalid
        }
}
