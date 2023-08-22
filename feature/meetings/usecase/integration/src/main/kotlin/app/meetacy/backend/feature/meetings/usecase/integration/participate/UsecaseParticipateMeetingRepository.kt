package app.meetacy.backend.feature.meetings.usecase.integration.participate

import app.meetacy.backend.feature.meetings.endpoints.participate.ParticipateMeetingRepository
import app.meetacy.backend.feature.meetings.endpoints.participate.ParticipateMeetingResult
import app.meetacy.backend.types.serializable.access.AccessIdentity
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.meetings.MeetingIdentity
import app.meetacy.backend.types.serializable.meetings.type
import app.meetacy.backend.feature.meetings.usecase.participate.ParticipateMeetingUsecase

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
            ParticipateMeetingUsecase.Result.AlreadyParticipant ->
                ParticipateMeetingResult.AlreadyParticipant
        }
}
