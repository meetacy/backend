package app.meetacy.backend.usecase.integration.meetings.avatar.delete

import app.meetacy.backend.endpoint.meetings.avatar.delete.DeleteMeetingAvatarParams
import app.meetacy.backend.endpoint.meetings.avatar.delete.DeleteMeetingAvatarRepository
import app.meetacy.backend.endpoint.meetings.avatar.delete.DeleteMeetingAvatarResult
import app.meetacy.backend.usecase.meetings.avatar.delete.DeleteMeetingAvatarUsecase

class UsecaseDeleteMeetingAvatarRepository(
    private val usecase: DeleteMeetingAvatarUsecase
) : DeleteMeetingAvatarRepository {
    override suspend fun deleteAvatar(
        deleteMeetingAvatarParams: DeleteMeetingAvatarParams
    ): DeleteMeetingAvatarResult = with(deleteMeetingAvatarParams) {
        when (usecase.deleteAvatar(accessIdentity.type(), meetingIdentity.type())) {
            DeleteMeetingAvatarUsecase.Result.InvalidIdentity ->
                DeleteMeetingAvatarResult.InvalidAccessIdentity
            DeleteMeetingAvatarUsecase.Result.MeetingNotFound ->
                DeleteMeetingAvatarResult.MeetingNotFound
            DeleteMeetingAvatarUsecase.Result.Success ->
                DeleteMeetingAvatarResult.Success
        }
    }
}