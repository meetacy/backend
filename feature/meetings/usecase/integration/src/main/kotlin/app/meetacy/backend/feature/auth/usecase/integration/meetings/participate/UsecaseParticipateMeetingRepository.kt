package app.meetacy.backend.feature.auth.usecase.integration.meetings.participate

import app.meetacy.backend.endpoint.meetings.participate.ParticipateMeetingRepository
import app.meetacy.backend.endpoint.meetings.participate.ParticipateMeetingResult
import app.meetacy.backend.feature.auth.usecase.meetings.participate.ParticipateMeetingUsecase
import app.meetacy.backend.types.serializable.access.AccessIdentity
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.meeting.MeetingIdentity
import app.meetacy.backend.types.serializable.meeting.type

class UsecaseParticipateMeetingRepository(
    private val usecase: ParticipateMeetingUsecase
) : ParticipateMeetingRepository {

    override suspend fun participateMeeting(
        meetingIdentity: MeetingIdentity,
        accessIdentity: AccessIdentity
    ): ParticipateMeetingResult =
        when (usecase.participateMeeting(meetingIdentity.type()!!, accessIdentity.type())) {
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
