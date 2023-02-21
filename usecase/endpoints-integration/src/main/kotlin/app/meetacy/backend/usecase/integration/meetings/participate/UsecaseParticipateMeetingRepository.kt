package app.meetacy.backend.usecase.integration.meetings.participate

import app.meetacy.backend.endpoint.meetings.participate.ParticipateMeetingRepository
import app.meetacy.backend.endpoint.meetings.participate.ParticipateMeetingResult
import app.meetacy.backend.types.AccessIdentity
import app.meetacy.backend.types.MeetingIdentity
import app.meetacy.backend.usecase.meetings.participate.ParticipateMeetingUsecase

class UsecaseParticipateMeetingRepository(
    private val usecase: ParticipateMeetingUsecase
) : ParticipateMeetingRepository {

    override suspend fun participateMeeting(
        meetingIdentity: MeetingIdentity,
        accessIdentity: AccessIdentity
    ): ParticipateMeetingResult =
        when (usecase.participateMeeting(meetingIdentity, accessIdentity)) {
            is ParticipateMeetingUsecase.Result.Success ->
                ParticipateMeetingResult.Success

            ParticipateMeetingUsecase.Result.MeetingNotFound ->
                ParticipateMeetingResult.MeetingNotFound

            ParticipateMeetingUsecase.Result.TokenInvalid ->
                ParticipateMeetingResult.InvalidIdentity
            ParticipateMeetingUsecase.Result.MeetingAlreadyParticipate ->
                ParticipateMeetingResult.MeetingAlreadyParticipate

            ParticipateMeetingUsecase.Result.FinishedMeeting ->
                ParticipateMeetingResult.FinishedMeeting
        }
}
