package app.meetacy.backend.usecase.integration.meetings.participate

import app.meetacy.backend.endpoint.meetings.participate.ParticipateMeetingRepository
import app.meetacy.backend.endpoint.meetings.participate.ParticipateMeetingResult
import app.meetacy.backend.endpoint.meetings.participate.ParticipateParam
import app.meetacy.backend.types.access.AccessIdentity
import app.meetacy.backend.types.meeting.MeetingIdentity
import app.meetacy.backend.usecase.meetings.participate.ParticipateMeetingUsecase

class UsecaseParticipateMeetingRepository(
    private val usecase: ParticipateMeetingUsecase
) : ParticipateMeetingRepository {

    override suspend fun participateMeeting(params: ParticipateParam): ParticipateMeetingResult =
        when (usecase.participateMeeting(params.meetingId.type(), params.token.type())) {
            is ParticipateMeetingUsecase.Result.Success ->
                ParticipateMeetingResult.Success

            ParticipateMeetingUsecase.Result.MeetingNotFound ->
                ParticipateMeetingResult.MeetingNotFound

            ParticipateMeetingUsecase.Result.TokenInvalid ->
                ParticipateMeetingResult.InvalidIdentity
            ParticipateMeetingUsecase.Result.MeetingAlreadyParticipate ->
                ParticipateMeetingResult.MeetingAlreadyParticipate
        }
}
