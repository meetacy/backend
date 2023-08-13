package app.meetacy.backend.feature.meetings.usecase.integration.delete

import app.meetacy.backend.feature.meetings.endpoints.delete.DeleteMeetingParams
import app.meetacy.backend.feature.meetings.endpoints.delete.DeleteMeetingRepository
import app.meetacy.backend.feature.meetings.endpoints.delete.DeleteMeetingResult
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.types.serializable.meetings.type
import app.meetacy.backend.feature.meetings.usecase.delete.DeleteMeetingUsecase

class UsecaseDeleteMeetingRepository(
    private val usecase: DeleteMeetingUsecase
) : DeleteMeetingRepository {
    override suspend fun deleteMeeting(
        deleteMeetingParams: DeleteMeetingParams
    ): DeleteMeetingResult = with(deleteMeetingParams) {
        when (usecase.deleteMeeting(token.type(), meetingId.type()!!)) {
            DeleteMeetingUsecase.Result.InvalidIdentity ->
                DeleteMeetingResult.InvalidIdentity
            DeleteMeetingUsecase.Result.MeetingNotFound ->
                DeleteMeetingResult.MeetingNotFound
            DeleteMeetingUsecase.Result.Success ->
                DeleteMeetingResult.Success
        }
    }
}