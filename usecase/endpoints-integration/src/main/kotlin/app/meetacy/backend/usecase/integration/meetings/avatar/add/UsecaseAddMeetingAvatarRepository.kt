package app.meetacy.backend.usecase.integration.meetings.avatar.add

import app.meetacy.backend.endpoint.meetings.avatar.add.AddMeetingAvatarParams
import app.meetacy.backend.endpoint.meetings.avatar.add.AddMeetingAvatarRepository
import app.meetacy.backend.endpoint.meetings.avatar.add.AddMeetingAvatarResult
import app.meetacy.backend.usecase.meetings.avatar.add.AddMeetingAvatarUsecase

class UsecaseAddMeetingAvatarRepository(
    private val usecase: AddMeetingAvatarUsecase
) : AddMeetingAvatarRepository {
    override suspend fun addAvatar(
        addMeetingAvatarParams: AddMeetingAvatarParams
    ): AddMeetingAvatarResult = with(addMeetingAvatarParams) {
        when (usecase.addAvatar(token.type(), meetingId.type(),  fileId.type())) {
            AddMeetingAvatarUsecase.Result.InvalidFileIdentity ->
                AddMeetingAvatarResult.InvalidMeetingFileIdentity
            AddMeetingAvatarUsecase.Result.InvalidIdentity ->
                AddMeetingAvatarResult.InvalidAccessIdentity
            AddMeetingAvatarUsecase.Result.MeetingNotFound ->
                AddMeetingAvatarResult.MeetingNotFound
            AddMeetingAvatarUsecase.Result.Success ->
                AddMeetingAvatarResult.Success
        }
    }
}