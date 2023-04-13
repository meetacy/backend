package app.meetacy.backend.usecase.integration.meetings.edit

import app.meetacy.backend.endpoint.meetings.edit.EditMeetingParams
import app.meetacy.backend.endpoint.meetings.edit.EditMeetingRepository
import app.meetacy.backend.endpoint.meetings.edit.EditMeetingResult
import app.meetacy.backend.usecase.integration.types.mapToFullMeeting
import app.meetacy.backend.usecase.meetings.edit.EditMeetingUsecase

class UsecaseEditMeetingRepository(
    private val usecase: EditMeetingUsecase
) : EditMeetingRepository {

    override suspend fun editMeeting(
        editMeetingParams: EditMeetingParams
    ): EditMeetingResult = with(editMeetingParams) {
        when (
            usecase.editMeeting(
                token.type(), meetingId.type(), avatarId?.type(), deleteAvatar, title, description, location?.type(), date?.type(), visibility?.mapToFullMeeting()
            )
        ) {
            EditMeetingUsecase.Result.InvalidAccessIdentity ->
                EditMeetingResult.InvalidAccessIdentity
            EditMeetingUsecase.Result.InvalidAvatarId ->
                EditMeetingResult.InvalidAvatarId
            EditMeetingUsecase.Result.InvalidMeetingId ->
                EditMeetingResult.InvalidMeetingId
            EditMeetingUsecase.Result.InvalidUtf8String ->
                EditMeetingResult.InvalidUtf8String
            EditMeetingUsecase.Result.NullEditParameters ->
                EditMeetingResult.NullEditParameters
            EditMeetingUsecase.Result.Success ->
                EditMeetingResult.Success
        }
    }
}
