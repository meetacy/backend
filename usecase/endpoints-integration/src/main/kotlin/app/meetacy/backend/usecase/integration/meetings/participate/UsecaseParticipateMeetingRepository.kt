package app.meetacy.backend.usecase.integration.meetings.participate

import app.meetacy.backend.endpoint.meetings.participate.ParticipateMeetingRepository
import app.meetacy.backend.endpoint.meetings.participate.ParticipateMeetingResult
import app.meetacy.backend.types.AccessHash
import app.meetacy.backend.types.AccessToken
import app.meetacy.backend.types.MeetingId
import app.meetacy.backend.usecase.meetings.ParticipateMeetingUsecase

class UsecaseParticipateMeetingRepository(
    private val usecase: ParticipateMeetingUsecase
) : ParticipateMeetingRepository {

    override suspend fun participateMeeting(
        meetingId: MeetingId,
        meetingAccessHash: AccessHash,
        accessToken: AccessToken
    ): ParticipateMeetingResult =

        when (usecase.participateMeeting(meetingId, meetingAccessHash, accessToken)) {
            is ParticipateMeetingUsecase.Result.Success ->
                ParticipateMeetingResult.Success

            ParticipateMeetingUsecase.Result.MeetingNotFound ->
                ParticipateMeetingResult.MeetingNotFound

            ParticipateMeetingUsecase.Result.TokenInvalid ->
                ParticipateMeetingResult.TokenInvalid
        }
}
