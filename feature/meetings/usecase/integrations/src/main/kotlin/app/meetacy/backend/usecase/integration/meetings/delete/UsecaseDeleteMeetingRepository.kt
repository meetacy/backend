package app.meetacy.backend.usecase.integration.meetings.delete

import app.meetacy.backend.endpoint.meetings.delete.DeleteMeetingParams
import app.meetacy.backend.endpoint.meetings.delete.DeleteMeetingRepository
import app.meetacy.backend.endpoint.meetings.delete.DeleteMeetingResult
import app.meetacy.backend.types.serializable.access.type
import app.meetacy.backend.usecase.meetings.delete.DeleteMeetingUsecase

class UsecaseDeleteMeetingRepository(
    private val usecase: DeleteMeetingUsecase
) : DeleteMeetingRepository {
    override suspend fun deleteMeeting(
        deleteMeetingParams: DeleteMeetingParams
    ): DeleteMeetingResult = with(deleteMeetingParams) {
        when (usecase.deleteMeeting(token.type(), meetingId.type())) {
            DeleteMeetingUsecase.Result.InvalidIdentity ->
                DeleteMeetingResult.InvalidIdentity
            DeleteMeetingUsecase.Result.MeetingNotFound ->
                DeleteMeetingResult.MeetingNotFound
            DeleteMeetingUsecase.Result.Success ->
                DeleteMeetingResult.Success
        }
    }
}